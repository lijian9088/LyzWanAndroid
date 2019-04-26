package com.lyz.lyzwanandroid.ui.module.project;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.ProjectTitle;
import com.lyz.lyzwanandroid.ui.adpter.ProjectTabPageFragmentAdapter;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.module.project.tabpage.ProjectTabPageMvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectMvpFragment extends BaseMvpFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ProjectTabPageFragmentAdapter fragmentAdapter;

    public static ProjectMvpFragment newInstance() {

        Bundle args = new Bundle();

        ProjectMvpFragment fragment = new ProjectMvpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_project;
    }

    @Override
    protected ProjectPresenter createPresenter() {
        ProjectPresenter presenter = new ProjectPresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter.getProjectTitle();
    }

    @Override
    public void setTitleData(List<ProjectTitle> data) {

        List<ProjectTabPageMvpFragment> fragmentList = new ArrayList<>();

        if (data != null && data.size() > 0) {
            for (ProjectTitle title : data) {
                tabLayout.addTab(tabLayout.newTab().setText(title.name));
                ProjectTabPageMvpFragment fragment = ProjectTabPageMvpFragment.newInstance(title.id);
                fragmentList.add(fragment);
            }

            setupTabPage(fragmentList, data);
        }

    }

    private void setupTabPage(List<ProjectTabPageMvpFragment> fragmentList, List<ProjectTitle> titleList) {
        fragmentAdapter = new ProjectTabPageFragmentAdapter(getFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
