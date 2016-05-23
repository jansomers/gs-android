package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import android.content.Intent;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.exams.domain.IndividualExamResponse;
import br.com.managersystems.guardasaude.exams.exammenu.information.anonymousexam.OnAnonymousExamRetrievedListener;
import br.com.managersystems.guardasaude.exams.exammenu.information.anonymousexam.OnAnonymousInformationRetrievedListener;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamOverviewInteractor implements IExamListInteractor {

    private final String BASE_URL = "https://www.guardasaude.com.br/";
    private ExamApi examApi;


    public ExamOverviewInteractor() {
        examApi = initiateRetrofit();
    }

    @Override
    public ExamApi initiateRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build())
                .build();
        return retrofit.create(ExamApi.class);
    }


    @Override
    public void getFirstExamList(final OnCallExamListFinishedListener listener, final String userName, final String token, final String orderBy, final String sortBy, final String maxValue, final String offsetValue, final String filterBy, final String accesRole,final String isEmergency) {
        if (examApi == null) {
            examApi = initiateRetrofit();
        }
        Call<ExamList> call = examApi.getExamsList(userName, token, orderBy, sortBy, maxValue, offsetValue, filterBy, accesRole,isEmergency);
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
    public void getNextExamList(final OnCallExamListFinishedListener listener, String userName, String token, String orderBy, String sortBy, String maxValue, String offsetValue, String filterBy, String accessRole) {
        if (examApi == null) {
            examApi = initiateRetrofit();
        }
        Call<ExamList> call = examApi.getExamsList(userName, token, orderBy, sortBy, maxValue, offsetValue, filterBy, accesRole,"false");
        call.enqueue(new Callback<ExamList>() {

            @Override
            public void onResponse(Call<ExamList> call, Response<ExamList> response) {
                if (response.body() != null) {listener.onSuccessGetNextExamList(response.body());
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
    public void associateNewExam(final OnCallExamListFinishedListener listener, String user, String token, String exId, String ePassCode) {
        if (examApi == null) {
            examApi = initiateRetrofit();
        }
        Call<AssociatedExamResponse> call = examApi.associateNewExam(user, token, exId, ePassCode);
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
                    listener.examRetrievedSuccess(response.body().getRows().get(0));
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
    public void getExam(final OnAnonymousInformationRetrievedListener listener, Intent intent) {
        Exam exam = intent.getParcelableExtra("exam");
        if (exam.getIdentification().isEmpty()) {
            Log.d(getClass().getSimpleName(), "Exam has no identification.. alerting listener!");
            listener.onExamFailure();
        } else {
            Log.d(getClass().getSimpleName(), "Exam was retrieved successfully... notifying listener!");
            listener.onExamReceived(exam);
        }
    }
}
