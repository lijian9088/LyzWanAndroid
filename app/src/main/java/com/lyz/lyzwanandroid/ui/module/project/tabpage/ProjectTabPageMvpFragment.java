package com.lyz.lyzwanandroid.ui.module.project.tabpage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.adpter.ProjectTabPageAdapter;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.module.web.WebActivity;
import com.lyz.lyzwanandroid.ui.module.web.WebFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/02/19
 * @Describe
 */
public class ProjectTabPageMvpFragment extends BaseMvpFragment<ProjectTabPagePresenter> implements ProjectTabPageContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView rv;

    private ProjectTabPageAdapter adapter;
    private int page = 1;

    private int currentCid;

    public static ProjectTabPageMvpFragment newInstance(int cid) {
        Bundle args = new Bundle();
        args.putInt("cid", cid);
        ProjectTabPageMvpFragment fragment = new ProjectTabPageMvpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_smartrefresh_rv;
    }

    @Override
    protected ProjectTabPagePresenter createPresenter() {
        ProjectTabPagePresenter presenter = new ProjectTabPagePresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProjectTabPageAdapter();
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<WanAndroidData>() {
            @Override
            public void onItemClick(int position, WanAndroidData data) {
                String link = data.link;
                startViaParent(WebFragment.newInstance(link));
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData();
            }
        });
    }

    public void refreshData() {
        clearData();
        page = 1;
        presenter.getProject(page, currentCid);
    }

    public void loadMoreData() {
        page++;
        presenter.getProject(page, currentCid);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        currentCid = arguments.getInt("cid");
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
    public void setItemData(List<WanAndroidData> data) {
        adapter.setData(data);
    }

    @Override
    public void appendItemData(List<WanAndroidData> data) {
        adapter.appendItems(data);
    }

    @Override
    public void clearData() {
        adapter.clearData();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        refreshData();
    }
}
