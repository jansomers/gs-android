package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.ui.fragments.FullScreenImageFragment;

import java.io.IOException;
import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter implements IFullScreenImageAdapter {

    private FullScreenImageFragment fragment;
    private ArrayList<Uri> uris;
    private ArrayList<Bitmap> images = new ArrayList<>();
    private LayoutInflater inflater;
    private Activity activity;

    public FullScreenImageAdapter(FullScreenImageFragment fragment,
                                  ArrayList<Uri> uris) throws IOException {
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        this.uris = uris;
        init();
    }

    @Override
    public void init() throws IOException {
        for (Uri uri:this.uris){
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),uri);
            images.add(bitmap);
        }
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.fullscreen_image, container, false);

        TouchImageView imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.img_display);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap bitmap = images.get(position);
        imgDisplay.setImageBitmap(bitmap);

        ImageView btnClose = (ImageView) viewLayout.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getFragmentManager().popBackStack();
            }
        });

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}