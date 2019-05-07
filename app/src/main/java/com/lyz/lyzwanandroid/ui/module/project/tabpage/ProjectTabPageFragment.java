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
import com.lyz.lyzwanandroid.ui.module.project.ProjectFragment;
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
public class ProjectTabPageFragment extends BaseMvpFragment<ProjectTabPagePresenter> implements ProjectTabPageContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView rv;

    private ProjectTabPageAdapter adapter;
    private int page;

    private int currentCid;
    private String currentTag;

    public static ProjectTabPageFragment newInstance(int cid, String tag) {
        Bundle args = new Bundle();
        args.putInt(ProjectFragment.ARGS_CID, cid);
        args.putString(ProjectFragment.ARGS_TAG, tag);
        ProjectTabPageFragment fragment = new ProjectTabPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_smartrefresh_rv;
    }

    @Override
    protected ProjectTabPagePresenter createPresenter() {
        return new ProjectTabPagePresenter();
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProjectTabPageAdapter();
        rv.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.decoration));
        rv.addItemDecoration(decoration);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<WanAndroidData>() {
            @Override
            public void onItemClick(int position, WanAndroidData data, View view) {
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
        resetPage();
        requestData();
    }

    public void loadMoreData() {
        page++;
        requestData();
    }

    private void resetPage() {
        if (ProjectFragment.TAG_PROJECT.equals(currentTag)) {
            page = 1;
        } else if (ProjectFragment.TAG_TREE.equals(currentTag)) {
            page = 0;
        }
    }

    public void requestData() {
        if (ProjectFragment.TAG_PROJECT.equals(currentTag)) {
            presenter.getProject(page, currentCid);
        } else if (ProjectFragment.TAG_TREE.equals(currentTag)) {
            presenter.getArticle(page, currentCid);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        currentCid = arguments.getInt(ProjectFragment.ARGS_CID);
        currentTag = arguments.getString(ProjectFragment.ARGS_TAG);
        resetPage();
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
