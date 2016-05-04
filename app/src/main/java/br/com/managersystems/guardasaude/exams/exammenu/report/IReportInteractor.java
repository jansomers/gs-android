package br.com.managersystems.guardasaude.exams.exammenu.report;

import android.content.Intent;

/**
 * Created by Jan on 27/04/2016.
 */
public interface IReportInteractor {
    void getReport(String identification, String token, String user);

    void getExam(Intent intent);
}
