package br.com.managersystems.guardasaude.ui.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.mainmenu.TabsPagerAdapter;
import br.com.managersystems.guardasaude.login.LoginPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainTabActivity extends AppCompatActivity {
    @Bind(R.id.pager)
    ViewPager viewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    
    Menu menu;
    private SharedPreferences sp;
    private String[] tabtitles;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        setTabTitles();
        setSharedPref();
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), new LoginPresenter(this, sp), tabtitles, sp));
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

    private void setTabTitles() {
        tabtitles = new String[]{(String) getResources().getText(R.string.exams), (String) getResources().getText(R.string.notifications), (String) getResources().getText(R.string.messages)};
    }

    @Override
    public void onBackPressed() {
        // Don't go back
    }

}

