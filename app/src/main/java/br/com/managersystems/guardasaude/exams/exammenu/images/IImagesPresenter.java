package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.Intent;
import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface IImagesPresenter {
    ArrayList<Bitmap> getImagesForExam() throws IOException;
    void retrieveExam(Intent intent);
    void retrieveImages(Exam exam);
}
