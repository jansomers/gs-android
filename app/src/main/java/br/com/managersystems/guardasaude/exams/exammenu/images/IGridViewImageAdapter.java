package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.util.ArrayList;

public interface IGridViewImageAdapter {
    /**
     * Convert each bitmap in examImages array to Uri with use of bitmapToUriConverter
     * Add Uris to arraylist of uris
     * @return arraylist of Uris
     */
    ArrayList<Uri> resizeBitmaps();

    /**
     * Convert bitmap to Uri using BitmapFactory
     * @param mBitmap : Bitmap that needs to be converted to Uri
     * @return Uri
     */
    Uri bitmapToUriConverter(Bitmap mBitmap);

}
