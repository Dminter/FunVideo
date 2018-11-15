package com.zncm.dminter.funvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.zncm.dminter.funvideo.data.Constants;
import com.zncm.dminter.funvideo.data.Live;
import com.zncm.dminter.funvideo.dbhelper.DbHelper;
import com.zncm.dminter.funvideo.ft.RecyclerViewFt;
import com.zncm.dminter.funvideo.utils.Xutils;

/**
 * Created by jiaomx on 2017/9/13.
 * 节目详情
 */

public class DetailsActivity extends AppCompatActivity {
    private Live live;
    private RecyclerViewFt recyclerViewFt;
    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            toolbar.setTitleTextColor(getResources().getColor(R.color.material_light_white));
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.mipmap.back);
        }
        live = (Live) getIntent().getSerializableExtra("Live");
        recyclerViewFt = new RecyclerViewFt();
        Bundle bundle = new Bundle();
        bundle.putString("tag", Constants.VIDEO_ZHIBO);
        bundle.putString("url", live.getName());
        if (Xutils.isNotEmptyOrNull(live.getName())) {
            toolbar.setTitle(live.getName());
        }
        recyclerViewFt.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, recyclerViewFt).commit();

    }


    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add("收藏").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == null || item.getTitle() == null) {
            return false;
        }
        if (item.getTitle().equals("收藏")) {
            DbHelper.getLiveDao().insert(live);
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }


}
