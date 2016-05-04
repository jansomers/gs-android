package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.ui.activities.FullScreenImageActivity;

public class GridViewImageAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Bitmap> examImages = new ArrayList<Bitmap>();
    private int imageWidth;

    public GridViewImageAdapter(Activity activity, ArrayList<Bitmap> examImages,
                                int imageWidth) {
        this.activity = activity;
        this.examImages = examImages;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this.examImages.size();
    }

    @Override
    public Object getItem(int position) {
        return this.examImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(activity);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bitmap = examImages.get(position);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
        imageView.setImageBitmap(bitmap);

        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    class OnImageClickListener implements View.OnClickListener {

        int postion;

        public OnImageClickListener(int position) {
            this.postion = position;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(activity, FullScreenImageActivity.class);
            i.putExtra("position", postion);
            activity.startActivity(i);
        }

    }


}
