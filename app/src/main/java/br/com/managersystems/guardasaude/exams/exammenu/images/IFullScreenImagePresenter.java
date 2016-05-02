package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.graphics.Bitmap;

import java.util.ArrayList;

public interface IFullScreenImagePresenter {
    ArrayList<Bitmap> getExamImages();
    Bitmap scaleImage(Bitmap bitmap);
}
