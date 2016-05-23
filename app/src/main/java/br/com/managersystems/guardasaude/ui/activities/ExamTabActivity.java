package br.com.managersystems.guardasaude.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.exammenu.ExamOneTabPagerAdapter;
import br.com.managersystems.guardasaude.exams.exammenu.ExamTabPresenter;
import br.com.managersystems.guardasaude.exams.exammenu.ExamTabsPagerAdapter;
import br.com.managersystems.guardasaude.exams.exammenu.IExamTabView;
import br.com.managersystems.guardasaude.login.LoginPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ExamTabActivity extends AppCompatActivity implements IExamTabView {
    @Bind(R.id.gs_maintab_activity_pager)
    ViewPager viewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private SharedPreferences sp;
    private String[] tabTitles;
    private Menu menu;
    private LoginPresenter loginPresenter;
    private boolean examStatusIsReady;
    private String role;


    @Override
    protected void onStart() {
        super.onStart();
        if (loginPresenter.validateToken(sp.getString("expires", "")) && !(sp.getString("role", "").isEmpty()))
            init();
        else navigateToLogin();
    }

    @Override
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
        getSharedPref();
        ExamTabPresenter examPresenter = new ExamTabPresenter(this);
        loginPresenter = new LoginPresenter(this, sp);
        examPresenter.retrieveExam(getIntent());
        setTabsPagerAdapter();
    }

    private void setTabsPagerAdapter() {
        if (role.equalsIgnoreCase(getResources().getString(R.string.patient_role))) {
            if (this.examStatusIsReady) {
                setAllTabsPagerAdapter();
            } else {
                setOneTabsPagerAdapter();
            }
        } else {
            setAllTabsPagerAdapter();
        }
    }

    private void setOneTabsPagerAdapter() {
        setOneTabTitle();
        ExamOneTabPagerAdapter examOneTabPagerAdapter = new ExamOneTabPagerAdapter(getSupportFragmentManager(), tabTitles);
        viewPager.setAdapter(examOneTabPagerAdapter);
    }

    private void setAllTabsPagerAdapter() {
        setAllTabTitles();
        ExamTabsPagerAdapter examTabPagerAdapter = new ExamTabsPagerAdapter(getSupportFragmentManager(), tabTitles, sp);
        viewPager.setAdapter(examTabPagerAdapter);
    }

    @Override
    public void setAllTabTitles() {
        tabTitles = new String[]{(String) getResources().getText(R.string.Information), (String) getResources().getText(R.string.Report), (String) getResources().getText(R.string.Images)};
    }

    public void setOneTabTitle() {
        tabTitles = new String[]{(String) getResources().getText(R.string.Information)};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        showOverflowMenu(false);
        return true;
    }
    /**
     * Shows or doesn't show the overview_group, depending on the parameter.
     * @param show boolean with true = show, false = don't show
     */
    private void showOverflowMenu(boolean show) {
        if (menu == null) return;
        menu.setGroupVisible(R.id.overview_group, show);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                loginPresenter.logout();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getSharedPref() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        role = sp.getString("role", "");
    }

    @Override
    public void navigateToLogin() {
        sp.edit().clear();
        sp.edit().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void setExamStatusIsReady(boolean isReady) {
        this.examStatusIsReady = isReady;
    }

}
