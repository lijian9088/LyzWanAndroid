package com.lyz.lyzwanandroid.ui.module.navigation;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.ui.adpter.TabAdapter;
import com.lyz.lyzwanandroid.ui.adpter.TagRvAdapter;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.widget.scroller.AdvertiseLinearSmoothScroller;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class NavigationFragment extends BaseMvpFragment<NavigationPresenter> implements NavigationContract.View {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rvTab)
    RecyclerView rvTab;
    @BindView(R.id.rvTag)
    RecyclerView rvTag;

    private TabAdapter tabAdapter;
    private TagRvAdapter tagRvAdapter;


    public static NavigationFragment newInstance() {

        Bundle args = new Bundle();

        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected NavigationPresenter createPresenter() {
        NavigationPresenter presenter = new NavigationPresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView(View view) {

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                startInitData();
            }
        });
        swipeLayout.setEnabled(false);

        initRvTab();
        initRvTag();
    }

    private void startInitData() {
        showLoading();
        presenter.getNavigationData();
    }

    private void initRvTab() {
        rvTab.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tabAdapter = new TabAdapter();
        rvTab.setAdapter(tabAdapter);
        tabAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<Navigation>() {
            @Override
            public void onItemClick(int position, Navigation data) {
                Logger.d("position:" + position);
                if (rvTag != null) {
                    rvTag.smoothScrollToPosition(position);
                }
            }
        });
        rvTab.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void initRvTag() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                AdvertiseLinearSmoothScroller linearSmoothScroller = new AdvertiseLinearSmoothScroller(recyclerView.getContext());
                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            }
        };

        rvTag.setLayoutManager(layoutManager);
        rvTag.setHasFixedSize(true);
        tagRvAdapter = new TagRvAdapter();
        rvTag.setAdapter(tagRvAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        startInitData();
    }

    @Override
    public void setNavigationData(List<Navigation> data) {

        initTabData(data);
        initTagData(data);

        hideLoading();
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
    public void clearData() {
        tabAdapter.clearData();
        tagRvAdapter.clearData();
    }

    private void initTabData(List<Navigation> data) {
        tabAdapter.setData(data);
    }

    private void initTagData(List<Navigation> data) {
        tagRvAdapter.setData(data);
    }
}
