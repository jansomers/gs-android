package br.com.managersystems.guardasaude.ui.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.mainmenu.IMainTabActivity;
import br.com.managersystems.guardasaude.exams.mainmenu.TabsPagerAdapter;
import br.com.managersystems.guardasaude.login.LoginPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainTabActivity extends AppCompatActivity implements IMainTabActivity{
    @Bind(R.id.pager)
    ViewPager viewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    
    Menu menu;
    private SharedPreferences sp;
    private String[] tabtitles;
    private LoginPresenter loginPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        setTitle("");
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        setSupportActionBar(toolbar);
        setTabTitles();
        getSharedPref();
        loginPresenter = new LoginPresenter(this, sp);
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), new LoginPresenter(this, sp), tabtitles, sp));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (loginPresenter.validateToken(sp.getString("expires", "")) && !(sp.getString("role", "").isEmpty())) init();
        else navigateToLogin();
    }

    @Override
    public void navigateToLogin() {
        sp.edit().clear();
        sp.edit().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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

    @Override
    public void getSharedPref() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void setTabTitles() {
        tabtitles = new String[]{(String) getResources().getText(R.string.exams), (String) getResources().getText(R.string.notifications), (String) getResources().getText(R.string.messages)};
    }

    @Override
    public void onBackPressed() {
        // Don't go back
    }

}

