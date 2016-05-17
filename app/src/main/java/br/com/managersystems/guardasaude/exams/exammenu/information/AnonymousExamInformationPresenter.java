package br.com.managersystems.guardasaude.exams.exammenu.information;


import android.content.Intent;

import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamInteractor;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.ui.activities.AnonymousExamInformationActivity;

public class AnonymousExamInformationPresenter implements OnAnonymousInformationRetrievedListener, IAnonymousExamInformationPresenter {
    private ExamInteractor interactor;
    private AnonymousExamInformationActivity activity;

    public AnonymousExamInformationPresenter(AnonymousExamInformationActivity anonymousExamInformationActivity) {
        interactor = new ExamInteractor();
        this.activity = anonymousExamInformationActivity;
    }
    @Override
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
