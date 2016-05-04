package br.com.managersystems.guardasaude.exams.exammenu.images;


import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamImageResponse;
import okhttp3.ResponseBody;

public interface OnImagesRetrievedListener {
    void onExamFailure();
    void onImageFailure();
    void onExamReceived(Exam exam);
    void onImageSuccess(ExamImageResponse response);
    void onAllImagesSuccess();
}
