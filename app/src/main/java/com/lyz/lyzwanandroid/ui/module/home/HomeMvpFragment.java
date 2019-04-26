package com.lyz.lyzwanandroid.ui.module.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.adpter.HomeAdapter;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.module.web.WebActivity;
import com.lyz.lyzwanandroid.ui.module.web.WebFragment;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author liyanze
 */
public class HomeMvpFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView rv;

    private HomeAdapter adapter;
    private int page = 1;

    public static HomeMvpFragment newInstance() {
        Bundle args = new Bundle();
        HomeMvpFragment fragment = new HomeMvpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_smartrefresh_rv;
    }

    @Override
    protected HomePresenter createPresenter() {
        HomePresenter presenter = new HomePresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView(View view) {

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeAdapter();
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<WanAndroidData>() {
            @Override
            public void onItemClick(int position, WanAndroidData data) {
                String link = data.link;
                WebActivity.goActivity(getActivity(), link);
//                WebFragment fragment = WebFragment.newInstance(link);
//                start(fragment);
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Logger.d("onRefresh");
                clearData();
                startInitAllData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Logger.d("onLoadMore:" + page);
                presenter.getArticle(page++);
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showLoading();
    }

    private void startInitAllData() {
        showLoading();
        presenter.initAllData();
    }

    @Override
    public void showLoading() {
        refreshLayout.autoRefresh();
    }

    @Override
    public void hideLoading(boolean success) {
        refreshLayout.finishRefresh(success);
    }

    @Override
    public void hideLoadMore(boolean success) {
        refreshLayout.finishLoadMore(success);
    }

    @Override
    public void setBannerData(List<Banner> data) {
        adapter.setBannerData(data);
    }

    @Override
    public void setItemData(List<WanAndroidData> data) {
        adapter.setData(data);
    }

    @Override
    public void appendItemData(List<WanAndroidData> data) {
        adapter.appendItems(data);
    }

    @Override
    public void clearData() {
        if (adapter != null) {
            adapter.clearData();
            page = 1;
        }
    }

}
