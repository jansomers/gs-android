package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.Intent;
import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface IImagesPresenter {
    /**
     * When no images are found show error to user
     * Decrypt base64 string to bitmap
     * Return list of bitmaps
     */
    ArrayList<Bitmap> getImagesForExam() throws IOException;

    /**
     * Call interactor to retrieve exam from intent
     * @param intent
     */
    void retrieveExam(Intent intent);

    /**
     * Call interactor to retrieve images for exam
     * @param exam
     */
    void retrieveImages(Exam exam);
}
