package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.Intent;

import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface IImagesInteractor {
    /**
     * Gets an exam from the intent
     * If the exam has no identification the listener's onExamFailure method is called upon.
     * Else calls the onExamSuccess method and passes the exams to the listener.
     * @param intent Intent object that holds the exam.
     */
    void getExam(Intent intent);

    /**
     * Makes a call to the examApi to get a certain image for an exam.
     * If the image is received calls listener's onImageSuccess method and passes an DocumentResponse
     * If all images are received calls listener's onAllImagesSuccess method.
     * @param exam Exam object which the image belongs to.
     * @param username String object representing the username of the user.
     * @param token String object representing the token.
     * @param exId String that holds the exam identification.
     * @param exDocId String that holds the document identification.
     */
    void getExamImage(Exam exam,String username, String token,String exId, String exDocId);
}
