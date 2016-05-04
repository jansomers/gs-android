package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamImageResponse;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImagesInteractor implements IImagesInteractor{
    private final String BASE_URL = "https://guardasaude.com.br/";
      ExamApi examApi;
    OnImagesRetrievedListener listener;

    public ImagesInteractor(OnImagesRetrievedListener listener) {
        this.listener = listener;
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
        Log.d(getClass().getSimpleName(), "Interactor is retrieving the exam...");
        Exam exam = intent.getParcelableExtra("exam");
        if (exam.getIdentification().isEmpty()) {
            Log.d(getClass().getSimpleName(), "Exam has no identification.. alerting listener!");
            listener.onExamFailure();
        } else {
            Log.d(getClass().getSimpleName(), "Exam was retrieved succesfully... notifying listener!");
            listener.onExamReceived(exam);
        }
    }

    @Override
    public void getExamImage(String user, String token,String exId, String exDocId) {
        if (examApi == null) initiateRetrofit();
        Call<ResponseBody> call = examApi.getExamImage(user,token,exId,exDocId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                        listener.onImageSuccess(response.body());
                    } else {
                        // TODO
                    }
                } else {
                    // TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //TODO ONFAILURE
                //listener.onImageFailure();
               listener.onImageFailure();
            }
        });
    }
}
