package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.graphics.Bitmap;

import java.util.ArrayList;

public interface IImagesPresenter {
    void InitializeGridLayout();
    int getScreenWidth();
    ArrayList<Bitmap> getImagesForExam();
    Bitmap scaleImage(Bitmap bitmap);
    int getColumnWidth();
}
