package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
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
    private int imagesCounter=0;

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
    public void getExamImage(final Exam exam,final String user,final String token,final String exId,final String exDocId) {
        if (examApi == null) initiateRetrofit();
        else {

                Call<ExamImageResponse> call = examApi.getExamImage(user, token, exId, exDocId);
                call.enqueue(new Callback<ExamImageResponse>() {
                    @Override
                    public void onResponse(Call<ExamImageResponse> call, Response<ExamImageResponse> response) {
                        imagesCounter++;
                        if(response.body()!=null) {
                            listener.onImageSuccess(response.body());
                            if (imagesCounter == exam.getImages().size()) {
                                listener.onAllImagesSuccess();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ExamImageResponse> call, Throwable t) {
                        listener.onImageFailure();

                    }
                });
        }

    }
}
