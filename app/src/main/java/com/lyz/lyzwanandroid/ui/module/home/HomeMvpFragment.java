package com.lyz.lyzwanandroid.ui.module.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.ui.adpter.HomeAdapter;
import com.lyz.lyzwanandroid.ui.base.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.listener.LyzRvListener;
import com.lyz.lyzwanandroid.ui.module.web.WebActivity;

import java.util.List;

import butterknife.BindView;

public class HomeMvpFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.recyclerView)
    RecyclerView rv;

    private HomeAdapter adapter;
    private int page = 0;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        HomePresenter presenter = new HomePresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView(View view) {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                startInitAllData();
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeAdapter();
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv.addOnScrollListener(new LyzRvListener() {
            @Override
            public void onEnd() {
                setFooterState(HomeAdapter.STATE_FOOTER_LOADING);
                presenter.getArticle(page++);
            }
        });
        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, WanAndroidData data) {
                String link = data.link;
                WebActivity.goActivity(getActivity(), link);
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        startInitAllData();
    }

    private void startInitAllData() {
        showLoading();
        presenter.initAllData();
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
    public void setBannerData(List<Banner> data) {
        if (data != null && data.size() > 0) {
            adapter.setBannerData(data);
        } else {
//            showNoBannerData
        }
    }

    @Override
    public void setItemData(List<WanAndroidData> data) {
        if (data != null && data.size() > 0) {
            adapter.setData(data);
        } else {
//            showNoData
        }
    }

    @Override
    public void appendItemData(List<WanAndroidData> data) {
        if (data == null || data.size() == 0) {
            setFooterState(HomeAdapter.STATE_FOOTER_NOMORE);
        } else {
            adapter.appendItems(data);
            setFooterState(HomeAdapter.STATE_FOOTER_HIDELOADING);
        }
    }

    @Override
    public void clearData() {
        if (adapter != null) {
            adapter.clearData();
            page = 0;
        }
    }

    @Override
    public void setFooterState(int footerState) {
        switch (footerState) {
            case HomeAdapter.STATE_FOOTER_LOADING:
                adapter.setFooterLoading();
                break;
            case HomeAdapter.STATE_FOOTER_NOMORE:
                adapter.setFooterNoMore();
                break;
            case HomeAdapter.STATE_FOOTER_HIDELOADING:
            default:
                adapter.setFooterHideLoading();
        }
    }

    @Override
    public void onInitAllDataFinish() {
        hideLoading();
    }

}
