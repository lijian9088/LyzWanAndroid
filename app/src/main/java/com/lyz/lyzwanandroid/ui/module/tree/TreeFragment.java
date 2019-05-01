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
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.module.project.tabpage.ProjectTabPageMvpFragment;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

public class TreeFragment extends BaseMvpFragment<TreePresenter> implements TreeContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView rv;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private TreeAdapter treeAdapter;

    public static TreeFragment newInstance() {
        TreeFragment fragment = new TreeFragment();
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

        treeAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TreeData>() {
            @Override
            public void onItemClick(int position, TreeData data) {
                Logger.d("onItemClick:" + position);
                int cid = data.id;
                startViaParent(ProjectTabPageMvpFragment.newInstance(cid));
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showLoading();
    }

    @Override
    public void setTreeData(List<TreeData> data) {
        treeAdapter.setData(data);
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
    public void clearData() {
        treeAdapter.clearData();
    }
}
