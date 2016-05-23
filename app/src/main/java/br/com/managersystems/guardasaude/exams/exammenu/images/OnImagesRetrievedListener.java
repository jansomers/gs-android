package br.com.managersystems.guardasaude.exams.exammenu.images;


import br.com.managersystems.guardasaude.exams.domain.DocumentResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface OnImagesRetrievedListener {
    /**
     * Notifies the fragment to show an error when the exam can't be retrieved.
     */
    void onExamFailure();

    /**
     * Notifies fragment to show  an error to  the user when the  image could not be found.
     */
    void onImageFailure();

    /**
     * Calls retrieveImages method after an exam was successfully retrieved.
     * @param exam Exam object that holds the images.
     */
    void onExamReceived(Exam exam);

    /**
     * DocumentResponse is added to the list of imageFiles after the image was successfully received
     * @param response DocumentResponse that represents the response of a request.
     */
    void onImageSuccess(DocumentResponse response);

    /**
     * Notifies the successful retrieval of all images to the fragment.
     */
    void onAllImagesSuccess();
}
