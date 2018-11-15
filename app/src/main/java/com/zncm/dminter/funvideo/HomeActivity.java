package com.zncm.dminter.funvideo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.zncm.dminter.funvideo.data.Constants;
import com.zncm.dminter.funvideo.data.VideoModel;
import com.zncm.dminter.funvideo.db.DaoSession;
import com.zncm.dminter.funvideo.db.VideoModelDao;
import com.zncm.dminter.funvideo.dbhelper.DbHelper;
import com.zncm.dminter.funvideo.ft.LiveListFt;
import com.zncm.dminter.funvideo.ft.RecyclerViewFt;
import com.zncm.dminter.funvideo.utils.Xutils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class HomeActivity extends AppCompatActivity {
    private Context ctx;
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private Fragment songListFt;
    private LiveListFt liveListFt;


    private Map<Integer, Fragment> mapFt = new HashMap<Integer, Fragment>();
    private ArrayList<String> programs = new ArrayList<>();


    private static final String SQL_DISTINCT_ENAME = "SELECT DISTINCT " + VideoModelDao.Properties.Tag.columnName + " FROM " + VideoModelDao.TABLENAME;

    public static List<String> listEName(DaoSession session) {
        ArrayList<String> result = new ArrayList<String>();
        Cursor c = session.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
        try {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ctx = this;
//        programs.add(new Program("本地", ""));
//        programs.add(new Program("本地2", ""));
//        programs.add(new Program("本地3", ""));

        programs.add("默认");
        programs.add("直播");
        programs.add("直播收藏");
        programs.add("收藏节目");


        List<String> programTmp = listEName(DbHelper.getDaoSession());

        if (Xutils.listNotNull(programTmp)) {
            programs.addAll(programTmp);
        }
        Xutils.debug("programTmp::" + programTmp);

        initView();
    }


    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xutils.debug("--setOnClickListener--");
                initMain();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(3);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
//        Xutils.initTabLayout(this, mTabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initMain() {
        finish();
        startActivity(new Intent(ctx, HomeActivity.class));
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return programs.get(position);
        }

        @Override
        public int getCount() {
            return programs.size();
        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                songListFt = new RecyclerViewFt();
                Bundle bundle = new Bundle();
                bundle.putString("tag", programs.get(position));
                songListFt.setArguments(bundle);
                fragment = songListFt;
                mapFt.put(position, songListFt);

            } else if (position == 1) {
                liveListFt = new LiveListFt();
                fragment = liveListFt;
                mapFt.put(position, liveListFt);
            }else if (position == 2) {
                liveListFt = new LiveListFt();
                Bundle bundle = new Bundle();
                bundle.putString("tag", Constants.VIDEO_LIKE);
                liveListFt.setArguments(bundle);
                fragment = liveListFt;
                mapFt.put(position, liveListFt);

            }else if (position == 3) {
                songListFt = new RecyclerViewFt();
                Bundle bundle = new Bundle();
                bundle.putString("tag", Constants.VIDEO_LIKE_VIDEO);
                songListFt.setArguments(bundle);
                fragment = songListFt;
                mapFt.put(position, songListFt);

            }

            return fragment;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu sub = menu.addSubMenu("");
        sub.setIcon(android.support.v7.appcompat.R.drawable.abc_ic_menu_overflow_material);
        sub.add(0, 1, 0, "添加节目");
        sub.add(0, 2, 0, "添加单");
        sub.add(0, 3, 0, "删除节目单");
        sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item == null || item.getTitle() == null) {
            return false;
        }

        if (item.getTitle().equals("md_refresh")) {
        }

        switch (item.getItemId()) {
            case 1:
                dlgEdit(null);
                break;
            case 2:
                try {
                    String tag = new Random().nextInt(100) + "";
                    ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData abc = myClipboard.getPrimaryClip();
                    ClipData.Item itemClipData = abc.getItemAt(0);
                    String text = itemClipData.getText().toString();
                    if (Xutils.isNotEmptyOrNull(text)) {

                        String name = text;
                        String url = text;
                        if (text.contains("\n")) {
                            String lines[] = text.split("\n");
                            for (int i = 0; i < lines.length; i++) {
                                System.out.println(lines[i]);
                                String videoInfo = lines[i];
                                name = videoInfo;
                                url = videoInfo;
                                if (videoInfo.contains(",")) {
                                    String info[] = videoInfo.split(",");
                                    if (info.length > 1) {
                                        name = info[0];
                                        url = info[1];
                                    }
                                }
                                if (Xutils.isNotEmptyOrNull(text)) {
                                    VideoModel videoModel = new VideoModel(name, tag, url);
                                    DbHelper.getVideoModelDao().insertOrReplace(videoModel);
                                }
                            }
                        } else {
                            if (text.contains(",")) {
                                String info[] = text.split(",");
                                if (info.length > 1) {
                                    name = info[0];
                                    url = info[1];
                                }
                            }
                            VideoModel videoModel = new VideoModel(name, tag, url);
                            DbHelper.getVideoModelDao().insertOrReplace(videoModel);
                        }
                        initMain();
                    } else {
                        Xutils.tShort("剪切板没有数据~");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }



                break;
            case 3:

                String curTag = programs.get(mViewPager.getCurrentItem());
                QueryBuilder qb = DbHelper.getVideoModelDao().queryBuilder();

                if (Xutils.isEmptyOrNull(curTag)) {
                    qb.where(VideoModelDao.Properties.Tag.isNull());
                } else {
                    qb.where(VideoModelDao.Properties.Tag.eq(curTag), VideoModelDao.Properties.Tag.isNotNull());
                }

                List<VideoModel> videoModels = qb.list();
                if (Xutils.listNotNull(videoModels)) {
                    for (VideoModel tmp : videoModels
                            ) {
                        DbHelper.getVideoModelDao().delete(tmp);
                    }
                }
                initMain();
                break;

        }


        return false;
    }


    private void dlgEdit(final VideoModel program) {
        AlertDialog.Builder dialogEdit = new AlertDialog.Builder(ctx);
        View editView = LayoutInflater.from(ctx).inflate(R.layout.program_edit, null);
        final EditText editTitle = (EditText) editView.findViewById(R.id.editTitle);
        final EditText editUrl = (EditText) editView.findViewById(R.id.editUrl);
        final EditText editTag = (EditText) editView.findViewById(R.id.editTag);
        if (program != null) {
            if (Xutils.isNotEmptyOrNull(program.getName())) {
                editTitle.setText(program.getName());
            }
            if (Xutils.isNotEmptyOrNull(program.getUrl())) {
                editUrl.setText(program.getUrl());
            }
        }
        dialogEdit.setView(editView);
        dialogEdit.setTitle("添加节目")
                .setPositiveButton(program != null ? "修改" : "添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTitle.getText().toString().trim();
                        String url = editUrl.getText().toString().trim();
                        String tag = editTag.getText().toString().trim();
                        if (Xutils.isEmptyOrNull(title) || Xutils.isEmptyOrNull(url)) {
                            Xutils.tShort("节目和链接均不能为空~");
                        }
                        VideoModel mProgram = new VideoModel(title, tag, url);
                        if (program != null) {
                            mProgram.setId(program.getId());
                            DbHelper.getVideoModelDao().update(mProgram);
                        } else {
                            DbHelper.getVideoModelDao().insertOrReplace(mProgram);
                        }
                        initMain();
                    }
                });
        if (program != null) {
            dialogEdit.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DbHelper.getVideoModelDao().delete(program);
                    initMain();
                }
            });
            dialogEdit.setNeutralButton("克隆", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    VideoModel tmp = new VideoModel();
                    tmp.setName(new Random().nextInt(100) + "");
                    tmp.setUrl(program.getUrl());
                    DbHelper.getVideoModelDao().insertOrReplace(tmp);
                    initMain();
                }
            });
        }
        dialogEdit.show();
    }


//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
//            backToDesk(this);
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }
//
//    public static void backToDesk(Context activity) {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        activity.startActivity(intent);
//    }


    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }
}