package br.com.managersystems.guardasaude.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.io.IOException;
import java.util.ArrayList;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.exammenu.images.FullScreenImageAdapter;


public class FullScreenImageActivity extends Activity {
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    private ArrayList<Uri> images;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_view);

        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        images = i.getParcelableArrayListExtra("images");

        try {
            adapter = new FullScreenImageAdapter(FullScreenImageActivity.this, images);
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(position);

    }
}
