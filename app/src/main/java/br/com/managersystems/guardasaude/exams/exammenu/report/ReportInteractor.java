package br.com.managersystems.guardasaude.exams.exammenu.report;

import android.content.Intent;
import android.util.Log;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ReportResponse;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is an implementation of the IReportInteractor
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Also see:
 * @see IReportInteractor
 */
public class ReportInteractor implements IReportInteractor {
    private final String BASE_URL = "https://guardasaude.com.br/";
    OnReportRetrievedListener reportListener;

    ExamApi examApi;


    public ReportInteractor(OnReportRetrievedListener reportListener) {
        this.reportListener = reportListener;
        examApi = initiateRetrofit();
    }
    /**
     * Initiates the retrofit instances for the ExamApi.
     * @return ExamApi instance representing the client.
     */
    private ExamApi initiateRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ExamApi.class);
    }


    @Override
    public void getReport(final String examIdentification, final String token, final String user) {

        if (examApi == null) initiateRetrofit();
        Log.d(getClass().getSimpleName(), "making the getReport call");
        Call<ReportResponse> call = examApi.getReport(user, token, examIdentification);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.body().getReportContent()==null) {
                    Log.d(getClass().getSimpleName(), "report response body was empty.. alerting listener!");
                    reportListener.onFailure();
                } else {
                    Log.d(getClass().getSimpleName(), "report response successful... notifying listener!");
                    reportListener.onReportSuccess(response.body().getReportContent());
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                Log.d(getClass().getSimpleName(), "Report call failed... alerting listener!");
                reportListener.onFailure();

            }
        });
    }


    @Override
    public void getExam(Intent intent) {
        Log.d(getClass().getSimpleName(), "Interactor is retrieving the exam...");
        Exam exam = intent.getParcelableExtra("exam");
        if (exam.getIdentification().isEmpty()) {
            Log.d(getClass().getSimpleName(), "Exam has no identification.. alerting listener!");
            reportListener.onFailure();
        } else {
            Log.d(getClass().getSimpleName(), "Exam was retrieved successfully... notifying listener!");
            reportListener.onExamReceived(exam);
        }
    }
}
