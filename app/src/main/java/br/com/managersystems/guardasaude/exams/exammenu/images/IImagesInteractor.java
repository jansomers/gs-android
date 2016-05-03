package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.Intent;

public interface IImagesInteractor {
    void getExam(Intent intent);
    void getExamImage(String user, String token,String exId, String exDocId);
}
