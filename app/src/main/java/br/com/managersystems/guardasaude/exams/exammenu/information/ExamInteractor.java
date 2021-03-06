package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import br.com.managersystems.guardasaude.exams.domain.CommentResponse;
import br.com.managersystems.guardasaude.exams.domain.DocumentResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.PostCommentResponse;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamApi;
import br.com.managersystems.guardasaude.util.Base64Interactor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is an implementation of the IExamInteractor
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Also see:
 * @see IExamInteractor
 */
public class ExamInteractor implements  IExamInteractor {
    private final String BASE_URL= "https://guardasaude.com.br/";
    private int documentCounter = 0;
    OnInformationRetrievedListener examListener;
    Base64Interactor base64Interactor;
    ExamApi examApi;

    public ExamInteractor(OnInformationRetrievedListener examListener) {
        this.examListener = examListener;
        this.base64Interactor = new Base64Interactor();
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
    public void getExam(Intent intent) {
        Log.d(getClass().getSimpleName(), "Getting the exam...");
        Exam exam =intent.getParcelableExtra("exam");
        if (!(exam.getId() == 0)) {
            Log.d(getClass().getSimpleName(), "Exam retrieved successfully... Notifying the listener");
            examListener.onExamSuccess(exam);
        }
        else {
            Log.d(getClass().getSimpleName(), "Exam not retrieved... Alerting the listener");
            examListener.onExamFailure();
        }

    }

    @Override
    public void getCommentsForExam(final CharSequence exId, SharedPreferences sp) {
        if (examApi == null) initiateRetrofit();
        String user = base64Interactor.decodeBase64ToString(sp.getString("user", "").getBytes());
        String token = sp.getString("token", "");
        if (user.isEmpty() || token.isEmpty() || exId.toString().isEmpty()) {
            examListener.onCommentsRetrievedFailure();
        }
        else {
            Call<CommentResponse> call = examApi.getComments(user,token, exId.toString());
            call.enqueue(new Callback<CommentResponse>() {
                @Override
                public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                    examListener.onCommentsRetrievedSuccess(response.body().getComments());
                }

                @Override
                public void onFailure(Call<CommentResponse> call, Throwable t) {
                    examListener.onCommentsRetrievedFailure();

                }
            });
        }
    }

    @Override
    public void postNewComment(CharSequence exId, CharSequence comment, SharedPreferences sp) {
        if (examApi == null) initiateRetrofit();
        String user = base64Interactor.decodeBase64ToString(sp.getString("user", "").getBytes());
        String token = sp.getString("token", "");
        String encodedMessage = base64Interactor.encodeStringToBase64(comment.toString());
        if (comment.toString().isEmpty() || exId.toString().isEmpty() || user.isEmpty() || token.isEmpty()) {
            examListener.onPostCommentCallFailed();
        }
        else {
            Call<PostCommentResponse> call = examApi.postComment(user, token, exId.toString(), encodedMessage);
            call.enqueue(new Callback<PostCommentResponse>() {
                @Override
                public void onResponse(Call<PostCommentResponse> call, Response<PostCommentResponse> response) {
                    if (!(response.body().getResult().toLowerCase().equals("success"))) {
                        examListener.onCommentPostedFailure();
                    }
                    else {
                        examListener.onCommentPostedSuccess();
                    }
                }

                @Override
                public void onFailure(Call<PostCommentResponse> call, Throwable t) {
                    examListener.onCommentPostedFailure();
                }
            });

        }
    }



    @Override
    public void getDocument(final Exam exam,final String exId,final String exDocId,SharedPreferences sp) {
        if (examApi == null) initiateRetrofit();
        else {
            String user = base64Interactor.decodeBase64ToString(sp.getString("user", "").getBytes());
            String token = sp.getString("token", "");
            Call<DocumentResponse> call = examApi.getDocument(user, token, exId, exDocId);
            call.enqueue(new Callback<DocumentResponse>() {
                @Override
                public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {
                    if(response.body()!=null) {
                        documentCounter++;
                        examListener.onDocumentSuccess(response.body());
                        if(documentCounter>=exam.getDocuments().size()){
                            try {
                                examListener.onAllDocumentsSuccess();
                            } catch (IOException e) {
                                examListener.onDocumentFailure();
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<DocumentResponse> call, Throwable t) {
                    examListener.onDocumentFailure();
                }
            });
        }

    }

}

