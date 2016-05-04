package br.com.managersystems.guardasaude.exams.exammenu.images;

import android.content.Intent;

import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface IImagesInteractor {
    void getExam(Intent intent);
    void getExamImage(Exam exam,String user, String token,String exId, String exDocId);
}
