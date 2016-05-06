package br.com.managersystems.guardasaude.exams.exammenu.images;


import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamImageResponse;
import okhttp3.ResponseBody;

public interface OnImagesRetrievedListener {
    /**
     * The exam could not be found
     * Address fragment to show error to user
     */
    void onExamFailure();

    /**
     * The image could not be found
     * Address fragment to show error to user
     */
    void onImageFailure();

    /**
     * The exam was successfully received
     * calls retrieveImages method
     * @param exam
     */
    void onExamReceived(Exam exam);

    /**
     * The image was successfully received
     * ExamImageResponse is added to the list of imagefiles
     * @param response
     */
    void onImageSuccess(ExamImageResponse response);

    /**
     * All images were succesfully receives
     * communicates the successfull reception to fragment
     */
    void onAllImagesSuccess();
}
