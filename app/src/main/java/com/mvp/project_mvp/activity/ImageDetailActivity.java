package com.mvp.project_mvp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mvp.project_mvp.R;
import com.mvp.project_mvp.adapter.ImageDetailAdapter;
import com.mvp.project_mvp.mvp.bean.ImageDetailInfo;
import com.mvp.project_mvp.mvp.presenter.BasePresenter1;
import com.mvp.project_mvp.mvp.presenter.ImageDetailPresenterImpl;
import com.mvp.project_mvp.mvp.presenter.ToolBarItemPresenterImpl;
import com.mvp.project_mvp.mvp.view.BaseView;
import com.mvp.project_mvp.network.Api;
import com.mvp.project_mvp.utils.ActivityUtils;
import com.mvp.project_mvp.utils.CompetenceUtils;
import com.mvp.project_mvp.utils.UIUtils;
import com.mvp.project_mvp.widget.MyOnPageChangeListener;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;


/**
 * by y on 2016/4/29.
 */
public class ImageDetailActivity extends BaseActivity
        implements BaseView.ImageDetailView, BaseView.ToolBarItemView {


    @SuppressWarnings("unused")
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @SuppressWarnings("unused")
    @Bind(R.id.toolBar)
    Toolbar toolBar;

    private int id;
    private int pos;
    private LinkedList<ImageDetailInfo> list;
    private BasePresenter1.ImageDetailPresenter imageDetailPresenter;
    private BasePresenter1.ToolBarItemPresenter toolBarItemPresenter;
    private ImageDetailAdapter bigImageAdapter;


    public static void startIntent(int id, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        ActivityUtils.startActivity(ImageDetailActivity.class, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar.setTitle(UIUtils.getString(R.string.image_detail));
        setSupportActionBar(toolBar);
        CompetenceUtils.Storage();
        getBundle();
        init();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageDetailPresenter.competence(requestCode, grantResults);
    }


    private void init() {
        imageDetailPresenter = new ImageDetailPresenterImpl(this);
        toolBarItemPresenter = new ToolBarItemPresenterImpl(this);
        list = new LinkedList<>();
        imageDetailPresenter.requestNetWork(id);
        bigImageAdapter = new ImageDetailAdapter(list);

        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                toolBarItemPresenter.switchId(item.getItemId());
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new MyOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pos = position;
            }
        });

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_image_detail;
    }


    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            id = bundle.getInt("id");
        }
    }

    @Override
    public void netWorkError() {
        Toast(UIUtils.getString(R.string.network_error));
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showFoot() {

    }

    @Override
    public void hideFoot() {

    }


    @Override
    public void setData(List<ImageDetailInfo> datas) {
        if (!datas.isEmpty()) {
            list.addAll(datas);
            viewPager.setAdapter(bigImageAdapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public void switchShare() {
        ActivityUtils.share(Api.IMAGER_URL + list.get(pos).getSrc());
    }
}
