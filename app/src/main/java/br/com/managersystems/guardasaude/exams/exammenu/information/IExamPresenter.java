package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.content.SharedPreferences;

import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * This interface serves as a transportation layer between the view and the interactor.
 * In this case it handles request to retrieve exam information, retrieve exam comments and to save a new comment.
 *
 * The documentation briefly explain what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Also see:
 *
 * @see ExamInteractor
 * @see ExamPresenter
 * @see br.com.managersystems.guardasaude.ui.fragments.InformationFragment
 */
public interface IExamPresenter {

    /**
     * Forwards the request to retrieve information for an exam.
     * @param intent Intent object passed with the exam inside.
     */
    void retrieveInformation(Intent intent);
    /**
     * Forwards the request to retrieve comments for an exam.
     * @param exid CharSequence object representing the  exam identification.
     * @param sp SharedPreferences object that contents required parameters that the interactor needs.
     */
    void retrieveComments(CharSequence exid, SharedPreferences sp);
    /**
     * Forwards the request to retrieve comments for an exam.
     * @param comment Charsequence object representing the comment message.
     * @param exid CharSequence object representing the  exam identification.
     * @param sp SharedPreferences object that contents required parameters that the interactor needs.
     */
    void saveComment(CharSequence comment, CharSequence exid, SharedPreferences sp);

    /**
     * For each document in the exam
     * Forwards the request to retrieve document for this exam
     * @param exam The exam that contains the documents
     * @param sp SharedPreferences object that contents required parameters that the interactor needs.
     */
    void retrieveDocuments(Exam exam, SharedPreferences sp);
}
