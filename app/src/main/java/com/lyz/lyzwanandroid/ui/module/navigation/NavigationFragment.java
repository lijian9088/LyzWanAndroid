package com.lyz.lyzwanandroid.ui.module.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.databinding.FragmentNavigationBinding;
import com.lyz.lyzwanandroid.ui.adpter.TabAdapter;
import com.lyz.lyzwanandroid.ui.adpter.TagRvAdapter;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.widget.scroller.AdvertiseLinearSmoothScroller;
import com.orhanobut.logger.Logger;

import java.util.List;


/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class NavigationFragment extends BaseMvpFragment<NavigationPresenter, FragmentNavigationBinding> implements NavigationContract.View {

    SwipeRefreshLayout swipeLayout;
    RecyclerView rvTab;
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
    protected FragmentNavigationBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentNavigationBinding.inflate(inflater, container, false);
    }

    @Override
    protected NavigationPresenter createPresenter() {
        NavigationPresenter presenter = new NavigationPresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView() {
        swipeLayout = viewBinding.swipeLayout;
        rvTab = viewBinding.rvTab;
        rvTag = viewBinding.rvTag;

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
            public void onItemClick(int position, Navigation data, View view) {
                Logger.d("position:" + position);
                if (rvTag != null) {
                    rvTag.smoothScrollToPosition(position);
                }
            }
        });
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.decoration));
        rvTab.addItemDecoration(decoration);
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

//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext()) {
//            @Override
//            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
//                LinearSmoothScroller scroller = new LinearSmoothScroller(recyclerView.getContext()) {
//                    @Override
//                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//                        return 0.05f;
//                    }
//                };
//                scroller.setTargetPosition(position);
//                startSmoothScroll(scroller);
//            }
//        };

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
