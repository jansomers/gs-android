package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.Intent;

import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface IImagesInteractor {
    /**
     * Gets exam from intent
     * If exam has no identification call listeners onExamFailure method
     * Else call listeners onExamReceived method and pass exam
     * @param intent
     */
    void getExam(Intent intent);

    /**
     * Makes a call to the examApi to get a certain image for an exam
     * If image is received call listeners onImageSuccess method and pass ExamImageResponse
     * If all images are received call listeners onAllImagesSuccess method
     * @param exam
     * @param user
     * @param token
     * @param exId
     * @param exDocId
     */
    void getExamImage(Exam exam,String user, String token,String exId, String exDocId);
}
