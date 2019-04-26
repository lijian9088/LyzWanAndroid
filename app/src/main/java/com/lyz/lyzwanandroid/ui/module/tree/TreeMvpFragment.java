package com.lyz.lyzwanandroid.ui.module.tree;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.ui.adpter.TreeAdapter;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

public class TreeMvpFragment extends BaseMvpFragment<TreePresenter> implements TreeContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView rv;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private TreeAdapter treeAdapter;

    public static TreeMvpFragment newInstance() {
        TreeMvpFragment fragment = new TreeMvpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_smartrefresh_rv;
    }

    @Override
    protected TreePresenter createPresenter() {
        TreePresenter presenter = new TreePresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        treeAdapter = new TreeAdapter();
        rv.setAdapter(treeAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getTreeData();
            }
        });
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showLoading();
    }

    @Override
    public void setTreeData(List<TreeData> data) {
        if (data != null) {
            treeAdapter.setData(data);
        } else {
            showToast("获取数据出错");
        }
        hideLoading();
    }

    @Override
    public void showLoading() {
        refreshLayout.autoRefresh();
    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void clearData() {
        treeAdapter.clearData();
    }
}
