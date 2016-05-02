package br.com.managersystems.guardasaude.exams.exammenu.report;

import android.content.Intent;
import android.util.Log;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ReportResponse;
import br.com.managersystems.guardasaude.exams.exammenu.information.OnReportRetrievedListener;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jan on 27/04/2016.
 */
public class ReportInteractor implements IReportInteractor {
    private final String BASE_URL = "https://guardasaude.com.br/";
    OnReportRetrievedListener reportListener;

    ExamApi examApi;

    public ReportInteractor(OnReportRetrievedListener reportListener) {
        this.reportListener = reportListener;
        examApi = initiateRetrofit();

    }

    private ExamApi initiateRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ExamApi.class);
    }


    @Override
    public void getReport(final String identification, final String token, final String user) {

        if (examApi == null) initiateRetrofit();
        Log.d(getClass().getSimpleName(), "making the getReport call");
        Call<ReportResponse> call = examApi.getReport(user, token, identification);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.body().getReportContent().isEmpty()) {
                    Log.d(getClass().getSimpleName(), "report response body was empty.. alerting listener!");
                    reportListener.onFailure();
                } else {
                    Log.d(getClass().getSimpleName(), "report response succesful... notifying listener!");
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
            Log.d(getClass().getSimpleName(), "Exam was retrieved succesfully... notifying listener!");
            reportListener.onExamReceived(exam);
        }
    }
}
