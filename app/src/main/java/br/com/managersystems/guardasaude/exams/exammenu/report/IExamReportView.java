package br.com.managersystems.guardasaude.exams.exammenu.report;

/**
 * This interface consists of methods which are important for the implementation.
 * All methods should not manipulate domain data in any way and is used purely for view purposes.
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementation:
 * @see br.com.managersystems.guardasaude.ui.fragments.ReportFragment
 */
public interface IExamReportView {
    /**
     * Shows the report text decoded from HTML.
     * @param report String object representing the html.
     */
    void showReport(String report);

    /**
     * Alerts the user that no report is present or found.
     */
    void showReportError();
}
