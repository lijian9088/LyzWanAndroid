package com.lyz.lyzwanandroid.ui.module.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.databinding.FragmentProjectBinding;
import com.lyz.lyzwanandroid.ui.adpter.ProjectTabPageFragmentAdapter;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.module.main.MainFragment;
import com.lyz.lyzwanandroid.ui.module.project.tabpage.ProjectTabPageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyanze
 */
public class ProjectFragment extends BaseMvpFragment<ProjectPresenter, FragmentProjectBinding> implements ProjectContract.View {

    public static final String TAG_PROJECT = "project";
    public static final String TAG_TREE = "tree";

    public static final String ARGS_TAG = "tag";
    public static final String ARGS_CID = "cid";
    public static final String ARGS_TREEDATA = "treeData";

    TabLayout tabLayout;
    ViewPager viewPager;

    private ProjectTabPageFragmentAdapter fragmentAdapter;
    private int currentCid;
    private String tag;
    private TreeData treeData;

    public static ProjectFragment newInstance() {

        Bundle args = new Bundle();

        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProjectFragment newInstance(int cid, String tag, TreeData treeData) {

        Bundle args = new Bundle();
        args.putInt(ARGS_CID, cid);
        args.putString(ARGS_TAG, tag);
        args.putParcelable(ARGS_TREEDATA, treeData);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FragmentProjectBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProjectBinding.inflate(inflater, container, false);
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected void initView() {
        tabLayout = viewBinding.tabLayout;
        viewPager = viewBinding.viewPager;

        Bundle bundle = getArguments();
        currentCid = bundle.getInt(ARGS_CID, -1);
        tag = bundle.getString(ARGS_TAG);
        treeData = bundle.getParcelable(ARGS_TREEDATA);
    }

    @Override
    protected boolean canSwipeBack() {
        return currentCid != -1;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (currentCid == -1) {
            presenter.getProjectTitle();
        } else {
            setTreeTitleData(treeData.children);
        }
    }

    @Override
    public void setTreeTitleData(List<TreeData> data) {
        List<ProjectTabPageFragment> fragmentList = new ArrayList<>();

        if (data != null && data.size() > 0) {
            for (TreeData treeData : data) {
                tabLayout.addTab(tabLayout.newTab().setText(treeData.name));
                ProjectTabPageFragment fragment;
                if (currentCid == -1) {
                    fragment = ProjectTabPageFragment.newInstance(treeData.id, TAG_PROJECT);
                } else {
                    fragment = ProjectTabPageFragment.newInstance(treeData.id, TAG_TREE);
                }
                fragmentList.add(fragment);
            }
            setupTabPage(fragmentList, data);
        }

    }

    private void setupTabPage(List<ProjectTabPageFragment> fragmentList, List<TreeData> titleList) {
        Fragment fragment = getParentFragment();
        FragmentManager manager;
        if (fragment instanceof MainFragment) {
            manager = getFragmentManager();
        } else {
            manager = getChildFragmentManager();
        }
        fragmentAdapter = new ProjectTabPageFragmentAdapter(manager, fragmentList, titleList);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
