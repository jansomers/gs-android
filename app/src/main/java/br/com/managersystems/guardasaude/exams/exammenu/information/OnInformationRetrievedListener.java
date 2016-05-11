package br.com.managersystems.guardasaude.exams.exammenu.information;

import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Comment;
import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * This interface consists of methods that are implemented by the presenter and are called upon by
 * the interactor,after the interactor handled a request by the presenter , either successfully
 * or unsuccessfully.
 *
 * In this case it will notify the view what should happen, regarding showing information or not,
 * and enabling comments or not.
 *
 * The documentation briefly explains what the methods do.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Interests:
 *
 * @see ExamPresenter
 * @see ExamInteractor
 * @see br.com.managersystems.guardasaude.ui.fragments.InformationFragment
 *
 */
public interface OnInformationRetrievedListener {

    /**
     * Notifies the view that an exam was retrieved unsuccessfully.
     */
    void onExamFailure();

    /**
     * Notifies the view that an exam was retrieved successfully.
     * @param exam Exam object that holds the data required by the view.
     */
    void onExamSuccess(Exam exam);

    /**
     * Notifies the view that the comments couldn't be retrieved.
     */
    void onCommentsRetrievedFailure();

    /**
     * Notifies the view that the comments were successfully retrieved.
     * @param comments Comments List object that holds the data required by the view.
     */
    void onCommentsRetrievedSuccess(List<Comment> comments);
    /**
     * Notifies the view that the post comment call didn't happen.
     */
    void onPostCommentCallFailed();
    /**
     * Notifies the view that the comment was successfully posted.
     */
    void onCommentPostedFailure();
    /**
    * Notifies the view that the comment was successfully posted.
    */
    void onCommentPostedSuccess();
}
