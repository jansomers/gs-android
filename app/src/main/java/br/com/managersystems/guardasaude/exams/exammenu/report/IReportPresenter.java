package br.com.managersystems.guardasaude.exams.exammenu.report;

import android.content.Intent;

import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * Created by Jan on 27/04/2016.
 */
public interface IReportPresenter {
    void retrieveExam(Intent intent);
    void retrieveReport(Exam exam);
}
