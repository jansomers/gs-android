package br.com.managersystems.guardasaude.exams.exammenu.report;

import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * This interface consists of methods that are implemented by the presenter and are called upon by
 * the interactor,after the interactor handled a request by the presenter , either successfully
 * or unsuccessfully.
 *
 * In this case it will notify the view what should happen, regarding showing reports or not.
 *
 * The documentation briefly explains what the methods do.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Interests:
 *
 * @see ReportPresenter
 * @see ReportInteractor
 * @see br.com.managersystems.guardasaude.ui.fragments.ReportFragment
 *
 */
public interface OnReportRetrievedListener {
    /**
     * Notifies the view that the report couldn't be retrieved.
     */
    void onFailure();

    /**
     * Notifies the view that the report was successfully retrieved.
     * @param report String object representing the html embedded report.
     */
    void onReportSuccess(String report);

    /**
     * Calls the method to retrieve a report in the presenter.
     * @param exam Exam object representing the exam that needs the report.
     */
    void onExamReceived(Exam exam);
}
