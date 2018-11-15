package com.zncm.dminter.funvideo.ft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zncm.dminter.funvideo.DetailsActivity;
import com.zncm.dminter.funvideo.R;
import com.zncm.dminter.funvideo.data.Constants;
import com.zncm.dminter.funvideo.data.Live;
import com.zncm.dminter.funvideo.dbhelper.DbHelper;
import com.zncm.dminter.funvideo.utils.Xutils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class LiveListFt extends Fragment {
    private View view;
    public ArrayList<Live> Lives = new ArrayList<>();
    public int playIndex = -1;
    private Activity ctx;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    public TwinklingRefreshLayout refreshLayout;
    String tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.song_ft, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    private void initView() {
        ctx = (Activity) getActivity();
        refreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(Lives);
        mRecyclerView.setAdapter(myAdapter);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                loadMore(true);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadMore(false);
            }
        });
        refreshLayout.setEnableLoadmore(false);

        if (getArguments() != null) {
            tag = getArguments().getString("tag");
        }
        loadMore(true);
    }

    private void loadMore(boolean isFirst) {
        if (Xutils.isNotEmptyOrNull(tag) && tag.equals(Constants.VIDEO_LIKE)) {
            Lives = new ArrayList<>();
            QueryBuilder qb = DbHelper.getLiveDao().queryBuilder();
            List<Live> livesTmp = qb.list();
            if (Xutils.listNotNull(livesTmp)) {
                Lives.addAll(livesTmp);
                myAdapter.setItems(Lives);
                refreshLayout.finishRefreshing();
            }
        } else {
            pullData();
        }
        refreshLayout.finishRefreshing();
    }


    private void pullData() {
        Lives = new ArrayList<>();
        Lives.add(new Live("爆娱乐"));
        Lives.add(new Live("东北虎直播"));
        Lives.add(new Live("飞鱼直播"));
        Lives.add(new Live("简秀直播"));
        Lives.add(new Live("爱美人"));
        Lives.add(new Live("花兔直播"));
        Lives.add(new Live("逗趣"));
        Lives.add(new Live("月舞直播"));
        Lives.add(new Live("百媚直播"));
        Lives.add(new Live("飞兔直播"));
        Lives.add(new Live("一直播"));
        Lives.add(new Live("思美人"));
        Lives.add(new Live("机器猫"));
        Lives.add(new Live("聊客直播"));
        Lives.add(new Live("爱吧"));
        Lives.add(new Live("陌秀直播"));
        Lives.add(new Live("苏曼直播"));
        Lives.add(new Live("媚娇直播"));
        Lives.add(new Live("御姐直播"));
        Lives.add(new Live("惊喜兔"));
        Lives.add(new Live("北极星"));
        Lives.add(new Live("妖媚家族"));
        Lives.add(new Live("猫咪直播"));
        Lives.add(new Live("秀色直播"));
        Lives.add(new Live("丹唇直播"));
        Lives.add(new Live("碟秀"));
        Lives.add(new Live("夜魅直播"));
        Lives.add(new Live("优衣秀"));
        Lives.add(new Live("带秀直播"));
        Lives.add(new Live("爱上直播"));
        Lives.add(new Live("九秀直播"));
        Lives.add(new Live("后宫直播"));
        Lives.add(new Live("绿茶直播"));
        Lives.add(new Live("小爱"));
        Lives.add(new Live("可乐直播"));
        Lives.add(new Live("芸云直播"));
        Lives.add(new Live("日播"));
        Lives.add(new Live("睡美人"));
        Lives.add(new Live("亚洲热"));
        Lives.add(new Live("虾播"));
        Lives.add(new Live("哈尼秀"));
        Lives.add(new Live("喜力"));
        Lives.add(new Live("爱享直播"));
        myAdapter.setItems(Lives);
        refreshLayout.finishRefreshing();
    }


    public class MyAdapter extends RecyclerView.Adapter<LiveViewHolder> {
        public ArrayList<Live> items = null;
        public LiveViewHolder holder = null;

        public MyAdapter(ArrayList<Live> items) {
            this.items = items;
        }


        public void setItems(ArrayList<Live> items) {
            this.items = items;
            notifyDataSetChanged();
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public LiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
            holder = new LiveViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(LiveViewHolder holder, final int position) {
            final Live Live = Lives.get(position);

            if (Live != null) {
                if (Live != null && !TextUtils.isEmpty(Live.getName())) {
                    holder.musicTitle.setText(Live.getName());
                }
            }


            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playIndex = position;
                    clickLive(Live);
                }
            });


        }

        @Override
        public int getItemCount() {
            return Lives.size();
        }
    }


    private void clickLive(Live live) {
        Intent intent = new Intent(ctx, DetailsActivity.class);
        intent.putExtra("Live", live);
        startActivity(intent);
    }


    class LiveViewHolder extends RecyclerView.ViewHolder {

        public TextView musicTitle;
        public CardView mCardView;

        public LiveViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                musicTitle = (TextView) itemView.findViewById(R.id.musicTitle);
                mCardView = (CardView) itemView.findViewById(R.id.mCardView);
            }
        }
    }


}
