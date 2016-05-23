package br.com.managersystems.guardasaude.ui.fragments;


import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.exammenu.images.FullScreenImageAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class FullScreenImageFragment extends Fragment{
    @Bind(R.id.gs_maintab_activity_pager)
    ViewPager viewPager;

    private View view;
    private FullScreenImageAdapter adapter;
    private int position;
    private ArrayList<Uri> uris;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fullscreenimage, container, false);
        ButterKnife.bind(this, view);

        try {
            adapter = new FullScreenImageAdapter(FullScreenImageFragment.this, uris);
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });

    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setUris(ArrayList<Uri> uris) {
        this.uris = uris;
    }

}
