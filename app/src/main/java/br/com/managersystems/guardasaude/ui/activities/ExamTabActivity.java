package br.com.managersystems.guardasaude.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.exammenu.ExamTabsPagerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ExamTabActivity extends FragmentActivity{
    @Bind(R.id.pager)
    ViewPager viewPager;

    private SharedPreferences sp;

    private String[] tabtitles;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        init();
        setSharedPref();
        setTabTitles();
        viewPager.setAdapter(new ExamTabsPagerAdapter(getSupportFragmentManager(), tabtitles,sp));
    }

    private void init() {
        setSharedPref();
        setTabTitles();
    }

    private void setTabTitles() {
        tabtitles = new String[]{(String) getResources().getText(R.string.Information), (String) getResources().getText(R.string.Report), (String) getResources().getText(R.string.Images)};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        showOverflowMenu(false);
        return true;
    }

    private void showOverflowMenu(boolean show) {
        if (menu == null) return;
        menu.setGroupVisible(R.id.overview_group, show);
    }

    public void setSharedPref() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

}
