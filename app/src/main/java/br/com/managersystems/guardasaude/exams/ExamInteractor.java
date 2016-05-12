package br.com.managersystems.guardasaude.exams;


import android.content.Intent;
import android.util.Log;

import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.exams.domain.IndividualExamResponse;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamApi;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.IExamListInteractor;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.OnCallExamListFinishedListener;
import br.com.managersystems.guardasaude.login.AnonymousInformationListener;
import br.com.managersystems.guardasaude.login.OnAnonymousExamRetrievedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamInteractor implements IExamListInteractor {

    private final String BASE_URL = "https://www.guardasaude.com.br/";
    private ExamApi examApi;


    public ExamInteractor() {
        examApi = initiateRetrofit();
    }

    @Override
    public ExamApi initiateRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ExamApi.class);
    }


    @Override
    public void getExamList(final OnCallExamListFinishedListener listener, final String userName, final String token, final String orderBy, final String sortBy, final String maxValue, final String offsetValue, final String filterBy, final String accesRole) {
        if (examApi == null) {
            examApi = initiateRetrofit();
        }
        Call<ExamList> call = examApi.getExamsList(userName, token, orderBy, sortBy, maxValue, offsetValue, filterBy, accesRole);
        call.enqueue(new Callback<ExamList>() {
            @Override
            public void onResponse(Call<ExamList> call, Response<ExamList> response) {
                if (response.body() != null) {
                    listener.onSuccessGetExamList(response.body());
                } else {
                    listener.onFailureGetExamList();
                }
            }

            @Override
            public void onFailure(Call<ExamList> call, Throwable t) {
                listener.onFailureGetExamList();
            }
        });
    }

    @Override
    public void associateNewExam(final OnCallExamListFinishedListener listener, String user, String token, String exid, String ePassCode) {
        if (examApi == null) {
            examApi = initiateRetrofit();
        }
        Call<AssociatedExamResponse> call = examApi.associateNewExam(user, token, exid, ePassCode);
        call.enqueue(new Callback<AssociatedExamResponse>() {
            @Override
            public void onResponse(Call<AssociatedExamResponse> call, Response<AssociatedExamResponse> response) {
                if (response.body() != null) {
                    listener.onSuccessFindNewExam(response.body());
                } else {
                    listener.onFailureFindNewExam();
                }
            }

            @Override
            public void onFailure(Call<AssociatedExamResponse> call, Throwable t) {
                listener.onFailureFindNewExam();
            }
        });
    }

    @Override
    public void getAnonymousExam(final OnAnonymousExamRetrievedListener listener, String accessCodeString, String examIdString) {
        if (examApi == null) {
            examApi = initiateRetrofit();
        }
        Call<IndividualExamResponse> call = examApi.associateAnonymousExam(examIdString, accessCodeString);
        call.enqueue(new Callback<IndividualExamResponse>() {
            @Override
            public void onResponse(Call<IndividualExamResponse> call, Response<IndividualExamResponse> response) {
                if (response.body().getRows().size()!=0) {
                    listener.examRetrievedSucces(response.body().getRows().get(0));
                } else {
                    listener.examRetrievedFailure();
                }
            }

            @Override
            public void onFailure(Call<IndividualExamResponse> call, Throwable t) {
                listener.examRetrievedFailure();
            }
        });
    }


    @Override
    public void getExam(final AnonymousInformationListener listener, Intent intent) {
        Exam exam = intent.getParcelableExtra("exam");
        if (exam.getIdentification().isEmpty()) {
            Log.d(getClass().getSimpleName(), "Exam has no identification.. alerting listener!");
            listener.onExamFailure();
        } else {
            Log.d(getClass().getSimpleName(), "Exam was retrieved succesfully... notifying listener!");
            listener.onExamReceived(exam);
        }
    }
}
