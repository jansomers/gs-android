package br.com.managersystems.guardasaude.exams.exammenu.information;

import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * Created by Jan on 27/04/2016.
 */
public interface OnReportRetrievedListener {
    void onFailure();
    void onReportSuccess(String report);
    void onExamReceived(Exam exam);
}
