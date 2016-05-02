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
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainTabActivity extends AppCompatActivity {
    @Bind(R.id.pager)
    ViewPager viewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

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
        tabtitles = new String[]{(String) getResources().getText(R.string.Exams), (String) getResources().getText(R.string.Notifications), (String) getResources().getText(R.string.Messages)};
        getSharedPref();
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), tabtitles,sp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //g etMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getSharedPref() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }
}

