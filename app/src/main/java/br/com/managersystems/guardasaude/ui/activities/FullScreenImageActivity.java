package br.com.managersystems.guardasaude.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.exammenu.images.FullScreenImageAdapter;
import br.com.managersystems.guardasaude.exams.exammenu.images.FullScreenImagePresenter;


public class FullScreenImageActivity extends Activity {
    private FullScreenImageAdapter adapter;
    private FullScreenImagePresenter fullScreenImagePresenter;
    private ViewPager viewPager;

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fullscreen_view);

            viewPager = (ViewPager) findViewById(R.id.pager);

            Intent i = getIntent();
            int position = i.getIntExtra("position", 0);

            fullScreenImagePresenter = new FullScreenImagePresenter(this);

            adapter = new FullScreenImageAdapter(FullScreenImageActivity.this,fullScreenImagePresenter.getExamImages());

            viewPager.setAdapter(adapter);

            viewPager.setCurrentItem(position);
        }
}
