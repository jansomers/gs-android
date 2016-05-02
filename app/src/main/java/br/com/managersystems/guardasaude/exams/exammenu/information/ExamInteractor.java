package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.util.Log;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jan on 26/04/2016.
 */
public class ExamInteractor implements  IExamInteractor {
    private final String BASE_URL= "https://guardasaude.com.br/";
    OnInformationRetrievedListener examListener;

    ExamApi examApi;

    public ExamInteractor(OnInformationRetrievedListener examListener) {
        this.examListener = examListener;
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
    public void getExam(Intent intent) {
        Log.d(getClass().getSimpleName(), "Getting the exam...");
        Exam exam =intent.getParcelableExtra("exam");
        if (!(exam.getId() == 0)) {
            Log.d(getClass().getSimpleName(), "Exam retrieved succesfully... Notifying the listener");
            examListener.onExamSuccess(exam);
        }
        else {
            Log.d(getClass().getSimpleName(), "Exam not retrieved... Alerting the listener");
            examListener.onFailure();
        }


    }

    }

