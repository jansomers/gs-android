package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

public interface IGridViewImageAdapter {
    /**
     * Converts each bitmap inside an array of images into a Uri making use of the
     * bitmapToUriConverter. Adds Uris to an ArrayList.
     * @return ArrayList object containg Uri objects.
     */
    ArrayList<Uri> resizeBitmaps();

    /**
     * Converts a bitmap to an Uri object using BitmapFactory
     * @param mBitmap : Bitmap objects that needs to be converted into a Uri
     * @return Uri object that represents the converted Bitmap.
     */
    Uri bitmapToUriConverter(Bitmap mBitmap);

}
