package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.ui.fragments.FullScreenImageFragment;

public class GridViewImageAdapter extends BaseAdapter implements IGridViewImageAdapter {

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
            imageView = new ImageView(activity.getApplicationContext());
            Bitmap bitmap = examImages.get(position);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
            imageView.setImageBitmap(bitmap);

            imageView.setOnClickListener(new OnImageClickListener(position));
        } else {
            imageView = (ImageView) convertView;
        }

        return imageView;
    }

    @Override
    public ArrayList<Uri> resizeBitmaps() {
        ArrayList<Uri> uris = new ArrayList<>();

        for (Bitmap bitmap : examImages) {
            uris.add(bitmapToUriConverter(bitmap));
        }

        return uris;
    }

    @Override
    public Uri bitmapToUriConverter(Bitmap mBitmap) {
        Uri uri = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = calculateInSampleSize(options, 100, 100);

            options.inJustDecodeBounds = false;
            Bitmap newBitmap = Bitmap.createScaledBitmap(mBitmap, 200, 200,
                    true);
            File file = new File(activity.getFilesDir(), "Image" + new Random().nextInt() + ".jpeg");
            FileOutputStream out = activity.openFileOutput(file.getName(),Context.MODE_PRIVATE);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            String realPath = file.getAbsolutePath();
            File f = new File(realPath);
            uri = Uri.fromFile(f);

        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
        return uri;
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    class OnImageClickListener implements View.OnClickListener {

        int position;

        public OnImageClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
                FullScreenImageFragment fullScreenImageFragment = new FullScreenImageFragment();
                fullScreenImageFragment.setPosition(position);
                fullScreenImageFragment.setUris(resizeBitmaps());
                activity.getFragmentManager().beginTransaction().replace(R.id.gs_images_fragment_layout,fullScreenImageFragment).addToBackStack(null).commit();
        }
    }

}
