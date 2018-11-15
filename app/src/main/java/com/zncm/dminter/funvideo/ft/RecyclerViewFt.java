package com.zncm.dminter.funvideo.ft;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.zncm.dminter.funvideo.R;
import com.zncm.dminter.funvideo.adapter.RecyclerItemNormalHolder;
import com.zncm.dminter.funvideo.adapter.RecyclerNormalAdapter;
import com.zncm.dminter.funvideo.data.Constants;
import com.zncm.dminter.funvideo.data.Live;
import com.zncm.dminter.funvideo.data.LiveInfo;
import com.zncm.dminter.funvideo.data.VideoModel;
import com.zncm.dminter.funvideo.db.VideoModelDao;
import com.zncm.dminter.funvideo.dbhelper.DbHelper;
import com.zncm.dminter.funvideo.utils.Xutils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerViewFt extends Fragment {


    String tag;
    String url;
    Context ctx;
    View view;

    @BindView(R.id.list_item_recycler)
    RecyclerView videoList;

    LinearLayoutManager linearLayoutManager;

    RecyclerNormalAdapter recyclerNormalAdapter;

    List<VideoModel> dataList = new ArrayList<>();

    boolean mFull = false;


    private OkHttpClient client;

    private final int SUCESS_STATUS = 1;
    private final int ERROR_STATUS = 0;
    private final int DOWNLOAD_SUCESS_STATUS = 2;
    private final int DOWNLOAD_ERROR_STATUS = -1;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCESS_STATUS:
                    try {


                        String str = msg.obj.toString();
                        if (!TextUtils.isEmpty(str)) {
                            Xutils.debug("str=>>" + str);
                            String arr[] = str.split("\n");
                            for (int i = 0; i < arr.length - 1; i++) {
                                System.out.println(arr[i]);
                                String tmp = arr[i];
                                VideoModel videoModel = null;
                                if (tmp.contains("|")) {
                                    String arr2[] = tmp.split("\\|");
                                    videoModel = new VideoModel("", arr2[1].substring(3), arr2[2].substring(3));
                                }
                                dataList.add(videoModel);
                            }
                            if (Xutils.listNotNull(dataList)) {
//                                recyclerBaseAdapter.setListData(dataList);
                                if (recyclerNormalAdapter != null)
                                    recyclerNormalAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ERROR_STATUS:
                    Xutils.tShort("请求出错~");
                    break;
                case DOWNLOAD_SUCESS_STATUS:
                    LiveInfo song = (LiveInfo) msg.obj;
                    if (song == null) {
                        return;
                    }

                    break;
                case DOWNLOAD_ERROR_STATUS:
                    Xutils.tShort("下载失败~");
                    break;
            }
        }


    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_recycler_view, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        tag = getArguments().getString("tag");
        url = getArguments().getString("url");
        if (tag == null) {
            tag = "";
        }
        Xutils.debug("url::" + tag);
        initView();
    }


    private void initView() {


        recyclerNormalAdapter = new RecyclerNormalAdapter(ctx, dataList);
        videoList = (RecyclerView) view.findViewById(R.id.list_item_recycler);
        linearLayoutManager = new LinearLayoutManager(ctx);
        videoList.setLayoutManager(linearLayoutManager);
        videoList.setAdapter(recyclerNormalAdapter);
        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemNormalHolder.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if (!mFull) {
                            GSYVideoPlayer.releaseAllVideos();
                            recyclerNormalAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        if (tag.equals(Constants.VIDEO_ZHIBO)) {
            pullData();
        }
        if (tag.equals(Constants.VIDEO_LIKE_VIDEO)) {
//            QueryBuilder qb = DbHelper.getLiveDao().queryBuilder();
//            List<Live> lives = qb.list();
//            if (Xutils.listNotNull(lives)) {
//                for (VideoModel tmp : videoModels
//                        ) {
//                    dataList.add(tmp);
//                }
//            }
        } else {
            resolveData();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }

    }


    private void resolveData() {
        if (Xutils.isNotEmptyOrNull(tag) && tag.equals(Constants.VIDEO_DEFAULT)) {
            for (int i = 1; i < 16; i++) {
                VideoModel videoModel = new VideoModel("CCTV-" + i, "http://ivi.bupt.edu.cn/hls/cctv" + i + ".m3u8");
                dataList.add(videoModel);
            }
        } else {
            QueryBuilder qb = DbHelper.getVideoModelDao().queryBuilder();
            qb.where(VideoModelDao.Properties.Tag.eq(tag), VideoModelDao.Properties.Url.isNotNull());
            List<VideoModel> videoModels = qb.list();
            if (Xutils.listNotNull(videoModels)) {
                for (VideoModel tmp : videoModels
                        ) {
                    dataList.add(tmp);
                }
            }
        }
        if (recyclerNormalAdapter != null)
            recyclerNormalAdapter.notifyDataSetChanged();
    }


    private void pullData() {
        if (Xutils.isEmptyOrNull(url)) {
            return;
        }

        url = "http://pz.haizisou.cn:88/" + url + ".txt";


        client = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(3 * 60, TimeUnit.SECONDS)
                .maxStale(3 * 60, TimeUnit.SECONDS)
                .build();
        try {
            Request request = new Request.Builder().url(url)
                    .cacheControl(cacheControl).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message message = new Message();
                    message.what = ERROR_STATUS;
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message = new Message();
                    message.what = SUCESS_STATUS;
                    message.obj = response.body().string();
                    handler.sendMessage(message);
                }
            });
        } catch (Exception e) {
            Xutils.tShort("链接不合法~");
            e.printStackTrace();
        }
    }

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
