package br.com.managersystems.guardasaude.exams.mainmenu;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.managersystems.guardasaude.ui.fragments.ExamOverviewFragment;
import br.com.managersystems.guardasaude.ui.fragments.MessagesFragment;
import br.com.managersystems.guardasaude.ui.fragments.NotificationFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 3;
    private String tabtitles[];
    SharedPreferences sharedPreferences;

    public TabsPagerAdapter(FragmentManager fm, String[] tabtitles, SharedPreferences sp) {
        super(fm);
        this.tabtitles = tabtitles;
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
                ExamOverviewFragment examOverviewFragment = new ExamOverviewFragment();
                examOverviewFragment.setSharedPreferences(sharedPreferences);
                return examOverviewFragment;

            case 1:
                NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;

            case 2:
                MessagesFragment messagesFragment = new MessagesFragment();
                return messagesFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
