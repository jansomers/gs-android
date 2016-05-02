package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.ui.activities.FullScreenImageActivity;

public class FullScreenImagePresenter implements IFullScreenImagePresenter {
    private FullScreenImageActivity fullScreenImageActivity;

    public FullScreenImagePresenter(FullScreenImageActivity fullScreenImageActivity) {
        this.fullScreenImageActivity = fullScreenImageActivity;
    }

    @Override
    public ArrayList<Bitmap> getExamImages() {
        final ArrayList<Bitmap> images = new ArrayList<>();
        TypedArray imagesRes = fullScreenImageActivity.getResources().obtainTypedArray(R.array.image_ids);

        for (int i = 0; i < imagesRes.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(fullScreenImageActivity.getResources(), imagesRes.getResourceId(i, -1));
            images.add(scaleImage(bitmap));
        }
        return images;
    }

    @Override
    public Bitmap scaleImage(Bitmap bitmap) {
        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
        return Bitmap.createScaledBitmap(bitmap, 512, nh, true);
    }

}
