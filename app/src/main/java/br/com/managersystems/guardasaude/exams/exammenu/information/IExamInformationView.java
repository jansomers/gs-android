package br.com.managersystems.guardasaude.exams.exammenu.information;


import java.io.IOException;
import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Comment;
import br.com.managersystems.guardasaude.exams.domain.DocumentResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * This interface consists of methods which are important for the implementation.
 * All methods should not manipulate domain data in any way and is used purely for view purposes.
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementation:
 * @see br.com.managersystems.guardasaude.ui.fragments.InformationFragment
 */
public interface IExamInformationView {
    /**
     * Shows the information section of an exam in the fragment.
     * @param exam Exam object retrieved from the presenter.
     */
    void showInformation(Exam exam);

    /**
     * Shows and expands the comments section below the information section.
     */
    void showComments();

    /**
     * Hides and collapses the comments section.
     */
    void hideComments();

    /**
     * Navigates to the Images fragment.
     */
    void navigateToImages();

    /**
     * Shows an error in case an exam could not be shown.
     */
    void showInformationError();

    /**
     * Disables the comments button and section.
     * This is for when a patient accesses the exam information.
     */
    void disableComments();

    /**
     * Enables the comments button and section.
     * This is only done when a professional accesses the exam information.
     * @param comments List of Comment objects to be displayed.
     */
    void enableComments(List<Comment> comments);

    /**
     * Shows an error when a comment was posted unsuccessfully.
     */
    void showCommentPostError();

    /**
     * Shows the new comment in the list.
     */
    void showNewComment();

    void documentNotFound();

    void showPdfDocument(DocumentResponse response) throws IOException;
}
