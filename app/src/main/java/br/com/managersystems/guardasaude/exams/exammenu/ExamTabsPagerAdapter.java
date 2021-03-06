package br.com.managersystems.guardasaude.exams.exammenu;


import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.managersystems.guardasaude.ui.fragments.ImagesFragment;
import br.com.managersystems.guardasaude.ui.fragments.InformationFragment;
import br.com.managersystems.guardasaude.ui.fragments.ReportFragment;

public class ExamTabsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[];
    private SharedPreferences sharedPreferences;

    public ExamTabsPagerAdapter(FragmentManager fm, String[] tabTitles, SharedPreferences sp) {
        super(fm);
        this.tabTitles = tabTitles;
        this.sharedPreferences = sp;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InformationFragment();

            case 1:
                return new ReportFragment();

            case 2:
                ImagesFragment imagesFragment = new ImagesFragment();
                imagesFragment.setSharedPreferences(sharedPreferences);
                return imagesFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
