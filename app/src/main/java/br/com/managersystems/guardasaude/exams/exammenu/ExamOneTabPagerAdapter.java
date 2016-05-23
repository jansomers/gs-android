package br.com.managersystems.guardasaude.exams.exammenu;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.managersystems.guardasaude.ui.fragments.InformationFragment;


public class ExamOneTabPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 1;
    private String tabTitles[];

    public ExamOneTabPagerAdapter(FragmentManager fm, String[] tabTitles) {
        super(fm);
        this.tabTitles = tabTitles;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               InformationFragment informationFragment = new InformationFragment();
                informationFragment.setDocAndImagesHidden(true);
                return informationFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
