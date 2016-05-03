package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.Intent;
import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;

public interface IImagesPresenter {
    void InitializeGridLayout();
    int getScreenWidth();
    ArrayList<Bitmap> getImagesForExam() throws IOException;
    Bitmap scaleImage(Bitmap bitmap);
    int getColumnWidth();
    void retrieveExam(Intent intent);
    void retrieveImages();
}
