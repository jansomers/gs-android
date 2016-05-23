package br.com.managersystems.guardasaude.exams.exammenu.report;

import android.content.Intent;

/**
 * This interface consists of methods needed to successfully handle calls to obtain the report,
 * associated to an exam.
 *
 * In every method, the listener is called, which is, in this case, an instance of the ReportPresenter.
 *
 * The documentation briefly explains what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Also see:
 *
 * @see ReportInteractor
 * @see ReportPresenter
 * @see OnReportRetrievedListener
 */
public interface IReportInteractor {

    /**
     * Gets the report for an exam using the api.
     * Notifies the listener if the report was successfully retrieved or not.
     *
     * @param examIdentification String object representing the identification of the exam.
     * @param token String object representing the current token.
     * @param user String object representing the username.
     */
    void getReport(String examIdentification, String token, String user);

    /**
     * Gets the exam from the intent.
     * Notifies the listener if the exam was successfully retrieved or not.
     * @param intent Intent object that holds the exam.
     */
    void getExam(Intent intent);
}
