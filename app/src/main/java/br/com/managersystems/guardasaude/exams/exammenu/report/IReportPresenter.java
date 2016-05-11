package br.com.managersystems.guardasaude.exams.exammenu.report;

import android.content.Intent;

import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * This interface serves as a transportation layer between the view and the interactor.
 * In this case it handles request to retrieve an exam and it's corresponding report.
 *
 * The documentation briefly explain what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Also see:
 *
 * @see ReportInteractor
 * @see ReportPresenter
 * @see br.com.managersystems.guardasaude.ui.fragments.ReportFragment
 */
public interface IReportPresenter {
    /**
     * Forwards the request to retrieve an exam.
     * @param intent Intent object that holds the exam.
     */
    void retrieveExam(Intent intent);

    /**
     * Forwards the request to retrieve a report
     * @param exam Exam object representing the corresponding exam.
     */
    void retrieveReport(Exam exam);
}
