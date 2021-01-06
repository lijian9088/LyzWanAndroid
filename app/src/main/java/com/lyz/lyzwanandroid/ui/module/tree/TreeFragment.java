package com.lyz.lyzwanandroid.ui.module.tree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.databinding.LayoutSmartrefreshRvBinding;
import com.lyz.lyzwanandroid.ui.adpter.TreeAdapter;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.module.project.ProjectFragment;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import static com.lyz.lyzwanandroid.ui.module.project.ProjectFragment.TAG_TREE;

public class TreeFragment extends BaseMvpFragment<TreePresenter, LayoutSmartrefreshRvBinding> implements TreeContract.View {

    RecyclerView rv;
    RefreshLayout refreshLayout;

    private TreeAdapter treeAdapter;

    public static TreeFragment newInstance() {
        TreeFragment fragment = new TreeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected LayoutSmartrefreshRvBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return LayoutSmartrefreshRvBinding.inflate(inflater, container, false);
    }

    @Override
    protected TreePresenter createPresenter() {
        TreePresenter presenter = new TreePresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView() {
        rv = viewBinding.recyclerView;
        refreshLayout = viewBinding.refreshLayout;

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
            public void onItemClick(int position, TreeData data, View view) {
                Logger.d("onItemClick:" + position);
                int cid = data.id;
                Logger.d("cid:" + cid);
                startViaParent(ProjectFragment.newInstance(cid, TAG_TREE, data));
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter.getTreeData();
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
