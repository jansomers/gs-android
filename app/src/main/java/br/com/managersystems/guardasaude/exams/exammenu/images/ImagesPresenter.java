package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamImage;
import br.com.managersystems.guardasaude.exams.domain.ExamImageResponse;
import br.com.managersystems.guardasaude.ui.fragments.ImagesFragment;
import br.com.managersystems.guardasaude.util.Base64Interactor;

public class ImagesPresenter implements IImagesPresenter, OnImagesRetrievedListener {
    private ImagesFragment imagesFragment;
    private ImagesInteractor interactor;
    Base64Interactor base64Interactor;
    SharedPreferences sp;
    List<ExamImageResponse> imagesFiles = new ArrayList<>();


    public ImagesPresenter(ImagesFragment imagesFragment, SharedPreferences sharedPreferences) {
        this.sp = sharedPreferences;
        base64Interactor = new Base64Interactor();
        this.imagesFragment = imagesFragment;
        this.interactor = new ImagesInteractor(this);
    }


    @Override
    public ArrayList<Bitmap> getImagesForExam() {
        final ArrayList<Bitmap> imageItems = new ArrayList<>();

        if (imagesFiles.size() <= 0) {
            imagesFragment.noImagesFound();
        } else {
            for (ExamImageResponse response : imagesFiles) {
                if(response.getDocumentValue()!=null) {
                    byte[] decryptedResponse = Base64.decode(response.getDocumentValue(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decryptedResponse, 0, decryptedResponse.length);
                    imageItems.add(bitmap);
                }
            }
        }

        return imageItems;
    }

    @Override
    public void retrieveExam(Intent intent) {
        interactor.getExam(intent);
    }

    @Override
    public void retrieveImages(Exam exam) {
        byte[] encryptedUser = sp.getString("user", null).getBytes();
        final String user = base64Interactor.decodeBase64ToString(encryptedUser);
        final String token = sp.getString("token", null);
        for (final ExamImage examImage : exam.getImages()) {
            interactor.getExamImage(exam,user, token, examImage.getExamIdentification(), examImage.getImageIdentification());
        }
    }


    @Override
    public void onExamFailure() {
        imagesFragment.noImagesFound();
    }

    @Override
    public void onImageFailure() {
        imagesFragment.noImagesFound();
    }

    @Override
    public void onExamReceived(Exam exam) {
        retrieveImages(exam);
    }

    @Override
    public void onImageSuccess(ExamImageResponse response) {
        imagesFiles.add(response);
    }

    @Override
    public void onAllImagesSuccess(){
        imagesFragment.imagesReceivedSucces();
    }
}
