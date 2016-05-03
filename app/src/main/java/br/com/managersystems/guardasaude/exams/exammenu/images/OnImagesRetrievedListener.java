package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamImageResponse;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface OnImagesRetrievedListener {
    void onExamFailure();
    void onImageFailure();
    void onExamReceived(Exam exam);
    void onImageSuccess(ResponseBody response);
}
