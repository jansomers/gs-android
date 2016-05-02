package br.com.managersystems.guardasaude.exams.exammenu.report;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.exammenu.information.OnReportRetrievedListener;
import br.com.managersystems.guardasaude.ui.fragments.ReportFragment;
import br.com.managersystems.guardasaude.util.Base64Interactor;

/**
 * Created by Jan on 27/04/2016.
 */
public class ReportPresenter implements IReportPresenter, OnReportRetrievedListener {
    IExamReportView reportFragment;
    ReportInteractor reportInteractor;
    SharedPreferences sp;

    public ReportPresenter(ReportFragment reportFragment) {
        this.reportFragment = reportFragment;
        reportInteractor = new ReportInteractor(this);
        sp = PreferenceManager.getDefaultSharedPreferences(reportFragment.getContext());

    }

    @Override
    public void onFailure() {
        Log.d(getClass().getSimpleName(), "Received interactor failure.. Forwarding to view");
        reportFragment.showError();

    }

    @Override
    public void onReportSuccess(String report) {
        Log.d(getClass().getSimpleName(), "Received report from interactor...Forwarding to view");
        reportFragment.showReport(report);
    }

    @Override
    public void onExamReceived(Exam exam) {
        Log.d(getClass().getSimpleName(), "Received exam from interactor... ");
        retrieveReport(exam);
    }

    @Override
    public void retrieveExam(Intent intent) {
        Log.d(getClass().getSimpleName(), "Received intent from view: Getting exam");
        reportInteractor.getExam(intent);
    }

    @Override
    public void retrieveReport(Exam exam) {
        Log.d(getClass().getSimpleName(), "Getting the report");
        reportInteractor.getReport(exam.getIdentification(), sp.getString("token", ""), new Base64Interactor().decodeBase64ToString(sp.getString("user", "").getBytes()));
    }
}
