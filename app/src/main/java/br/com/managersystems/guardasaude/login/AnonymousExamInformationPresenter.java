package br.com.managersystems.guardasaude.login;


import android.content.Intent;

import br.com.managersystems.guardasaude.exams.ExamInteractor;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.ui.activities.AnonymousExamInformationActivity;
import br.com.managersystems.guardasaude.ui.activities.LoginActivity;

public class AnonymousExamInformationPresenter implements AnonymousInformationListener {
    private ExamInteractor interactor;
    private AnonymousExamInformationActivity activity;

    public AnonymousExamInformationPresenter(AnonymousExamInformationActivity anonymousExamInformationActivity) {
        interactor = new ExamInteractor();
        this.activity = anonymousExamInformationActivity;
    }

    public void retrieveExam(Intent intent) {
        interactor.getExam(this,intent);
    }

    @Override
    public void onExamReceived(Exam exam) {
        activity.setExamInformation(exam);
    }

    @Override
    public void onExamFailure() {
        activity.examNotFound();
    }
}
