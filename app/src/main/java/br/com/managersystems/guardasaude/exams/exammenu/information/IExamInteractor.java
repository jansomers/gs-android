package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.content.SharedPreferences;

import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * This interface consists of methods needed to successfully handle calls to obtain the comments,
 * associated to an exam, and to post new comments to the data base.
 *
 * In every method, the listener is called, which is, in this case, an instance of the ExamPresenter.
 *
 * The documentation briefly explains what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Also see:
 *
 * @see ExamInteractor
 * @see ExamPresenter
 * @see OnInformationRetrievedListener
 */
public interface IExamInteractor {
    /**
     * Gets exam from intent
     * If exam has no identification calls listeners onExamFailure method
     * Else calls listeners onExamReceived method and passes exam
     * @param intent Intent object passed with the exam inside.
     */
    void getExam(Intent intent);

    /**
     * Gets the comments for an exam using the api.
     * Notifies the listener accordingly to success or failure.
     * @param exId CharSequence object representing the identification of the exam.
     * @param sp SharedPreferences object passed with the other  required parameters inside to make the call.
     */
    void getCommentsForExam(CharSequence exId, SharedPreferences sp);

    /**
     * Posts a new comment for an exam using the api.
     * Notifies the listener according to success or failure.
     * @param exId CharSequence object representing the identification of the exam.
     * @param comment CharSequence object representing the comment message.
     * @param sp SharedPreferences object passed with the other required parameters inside to make the call.
     */
    void postNewComment(CharSequence exId, CharSequence comment, SharedPreferences sp);

    void getDocument(Exam exam,final String exId,final String exDocId,SharedPreferences sp);
}
