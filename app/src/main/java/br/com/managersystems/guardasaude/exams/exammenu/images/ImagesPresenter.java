package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridView;
import java.util.ArrayList;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.ui.fragments.ImagesFragment;

public class ImagesPresenter implements IImagesPresenter {
    private ImagesFragment imagesFragment;
    private GridView gridView;
    private static final int GRID_PADDING = 1;
    private static final int NUM_OF_COLUMNS = 2;
    private int columnWidth;

    public ImagesPresenter(ImagesFragment imagesFragment, GridView gridView) {
        this.imagesFragment = imagesFragment;
        this.gridView = gridView;
        InitializeGridLayout();
    }

    @Override
    public void InitializeGridLayout() {
        Resources r = imagesFragment.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((getScreenWidth() - ((NUM_OF_COLUMNS + 1) * padding)) / NUM_OF_COLUMNS);

        gridView.setNumColumns(NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    @Override
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) imagesFragment.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) {
        // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

    @Override
    public ArrayList<Bitmap> getImagesForExam() {
        final ArrayList<Bitmap> imageItems = new ArrayList<>();
        //TODO get images for exam
        TypedArray imgs = imagesFragment.getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(imagesFragment.getResources(), imgs.getResourceId(i, -1));
            imageItems.add(scaleImage(bitmap));
        }
        return imageItems;
    }

    @Override
    public Bitmap scaleImage(Bitmap bitmap) {
        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
        return Bitmap.createScaledBitmap(bitmap, 512, nh, true);
    }

    @Override
    public int getColumnWidth() {
        return columnWidth;
    }

}
