package br.com.managersystems.guardasaude.exams.exammenu;


import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.managersystems.guardasaude.ui.fragments.ImagesFragment;
import br.com.managersystems.guardasaude.ui.fragments.InformationFragment;
import br.com.managersystems.guardasaude.ui.fragments.ReportFragment;

public class ExamOneTabPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 1;
    private String tabtitles[];

    public ExamOneTabPagerAdapter(FragmentManager fm, String[] tabtitles) {
        super(fm);
        this.tabtitles = tabtitles;
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
                informationFragment.setDocCommentImageButtonsNotVisible();
                return informationFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
