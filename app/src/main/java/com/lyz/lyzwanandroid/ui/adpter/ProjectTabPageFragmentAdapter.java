package com.lyz.lyzwanandroid.ui.adpter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.ui.module.project.tabpage.ProjectTabPageFragment;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/02/19
 * @Describe
 */
public class ProjectTabPageFragmentAdapter extends FragmentPagerAdapter {

    private final List<ProjectTabPageFragment> fragmentlist;
    private final List<TreeData> treeDataList;

    public ProjectTabPageFragmentAdapter(FragmentManager fm, List<ProjectTabPageFragment> fragmentlist, List<TreeData> treeDatas) {
        super(fm);
        this.fragmentlist = fragmentlist;
        this.treeDataList = treeDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist != null ? fragmentlist.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return treeDataList.get(position).name;
    }
}
