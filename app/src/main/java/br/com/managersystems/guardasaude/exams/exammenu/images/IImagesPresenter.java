package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.Intent;
import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface IImagesPresenter {
    /**
     * When no images are found alerts the view accordingly.
     * Decrypts a base64 String to a Bitmap.
     * @return ArrayList<Bitmap> object that represents the image.
     */
    ArrayList<Bitmap> getImagesForExam() throws IOException;

    /**
     * Calls the interactor to retrieve the exam from intent
     * @param intent Intent object that holds the exam.
     */
    void retrieveExam(Intent intent);

    /**
     * Calls the interactor to retrieve the images of an exam
     * @param exam Exam object that the images belong to.
     */
    void retrieveImages(Exam exam);
}
