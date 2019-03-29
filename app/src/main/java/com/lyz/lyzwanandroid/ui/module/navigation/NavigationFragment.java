package com.lyz.lyzwanandroid.ui.module.navigation;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.NavLinkBean;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.adpter.TabAdapter;
import com.lyz.lyzwanandroid.ui.adpter.TagAdapter;
import com.lyz.lyzwanandroid.ui.base.BaseFragment;
import com.lyz.lyzwanandroid.ui.listener.OnItemClickListener;
import com.lyz.lyzwanandroid.ui.listener.OnTagClickListener;
import com.lyz.lyzwanandroid.ui.module.web.WebActivity;
import com.lyz.lyzwanandroid.widget.TagTextView;
import com.lyz.lyzwanandroid.widget.scroller.AdvertiseLinearSmoothScroller;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.View {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rvTab)
    RecyclerView rvTab;
    @BindView(R.id.rvTag)
    RecyclerView rvTag;

    private TabAdapter tabAdapter;
    private TagAdapter tagAdapter;
    private ArrayList<String> titles;
    private HashMap<String, ArrayList<NavLinkBean>> map;

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
        tabAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (rvTag != null) {
                    rvTag.smoothScrollToPosition(position);
                }
            }
        });
        rvTab.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void initRvTag() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext()) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                AdvertiseLinearSmoothScroller linearSmoothScroller = new AdvertiseLinearSmoothScroller(recyclerView.getContext());
                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            }
        };
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        rvTag.setLayoutManager(layoutManager);
        tagAdapter = new TagAdapter();
        tagAdapter.setHasStableIds(true);
        rvTag.setAdapter(tagAdapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        startInitData();
    }

    @Override
    public void setNavigationData(List<Navigation> data) {

        titles = new ArrayList<>();
        map = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            Navigation navigation = data.get(i);
            List<WanAndroidData> wanAndroidDatas = navigation.articles;

            ArrayList<NavLinkBean> arrayList = new ArrayList<>();
            for (int j = 0; j < wanAndroidDatas.size(); j++) {
                WanAndroidData wanAndroidData = wanAndroidDatas.get(j);
                String chapterName = wanAndroidData.chapterName;
                String title = wanAndroidData.title;
                String link = wanAndroidData.link;

                if (j == 0) {
                    titles.add(chapterName);
                }
                NavLinkBean navLinkBean = new NavLinkBean();
                navLinkBean.chapterName = chapterName;
                navLinkBean.title = title;
                navLinkBean.link = link;
                arrayList.add(navLinkBean);

                map.put(titles.get(i), arrayList);
            }
        }

        initTabData();
        initTagData();

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

    }

    private void initTabData() {
        tabAdapter.setData(titles);
    }

    private void initTagData() {
        tagAdapter.setData(titles, map);

        tagAdapter.setOnTagClickListen(new OnTagClickListener() {
            @Override
            public void onClick(TagTextView tag) {
                NavLinkBean tagData = (NavLinkBean) tag.tagData;
                int position = tagData.position;
                String link = tagData.link;
                String chapterName = tagData.chapterName;
                String title = tagData.title;
                Logger.d("position:%d,link:%s,chapterName:%s,title:%s",
                        position, link, chapterName, title);
                WebActivity.goActivity(getActivity(), link);
            }
        });
    }
}
