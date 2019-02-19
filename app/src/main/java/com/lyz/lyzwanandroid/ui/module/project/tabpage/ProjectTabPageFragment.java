package com.lyz.lyzwanandroid.ui.module.project.tabpage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Project;
import com.lyz.lyzwanandroid.ui.adpter.ProjectTabPageAdapter;
import com.lyz.lyzwanandroid.ui.base.BaseFragment;
import com.lyz.lyzwanandroid.ui.base.BaseRecyclerViewWithHeaderAndFooterAdapter;
import com.lyz.lyzwanandroid.ui.listener.LyzRvListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/02/19
 * @Describe
 */
public class ProjectTabPageFragment extends BaseFragment<ProjectTabPagePresenter> implements ProjectTabPageContract.View {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.recyclerView)
    RecyclerView rv;

    private ProjectTabPageAdapter adapter;
    private int page = 1;

    private int currentCid;

    public static ProjectTabPageFragment newInstance(int cid) {
        Bundle args = new Bundle();
        args.putInt("cid", cid);
        ProjectTabPageFragment fragment = new ProjectTabPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_project_tabpage;
    }

    @Override
    protected ProjectTabPagePresenter createPresenter() {
        ProjectTabPagePresenter presenter = new ProjectTabPagePresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView(View view) {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                page = 1;
                presenter.getProject(page, currentCid);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProjectTabPageAdapter();
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv.addOnScrollListener(new LyzRvListener() {
            @Override
            public void onEnd() {
                setFooterState(BaseRecyclerViewWithHeaderAndFooterAdapter.STATE_FOOTER_LOADING);
                presenter.getProject(page++, currentCid);
            }
        });
        adapter.setOnItemClickListener(new BaseRecyclerViewWithHeaderAndFooterAdapter.OnItemClickListener<Project>() {
            @Override
            public void onItemClick(int position, Project data) {
                showToast("position:%d,title:%s", position, data.title);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        currentCid = arguments.getInt("cid");
        showLoading();
        page = 1;
        presenter.getProject(page, currentCid);
    }

    @Override
    public void showLoading() {
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void setItemData(List<Project> data) {
        adapter.setData(data);
        hideLoading();
    }

    @Override
    public void appendItemData(List<Project> data) {
        adapter.appendItems(data);
        hideLoading();
    }

    @Override
    public void clearData() {
        adapter.clearData();
    }

    @Override
    public void setFooterState(int footerState) {
        adapter.setFooterState(footerState);
    }
}
