package br.com.managersystems.guardasaude.exams.exammenu.information.anonymousexam;


import android.content.Intent;

import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamOverviewInteractor;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.ui.activities.AnonymousExamInformationActivity;

public class AnonymousExamInformationPresenter implements OnAnonymousInformationRetrievedListener, IAnonymousExamInformationPresenter {
    private ExamOverviewInteractor interactor;
    private AnonymousExamInformationActivity activity;

    public AnonymousExamInformationPresenter(AnonymousExamInformationActivity anonymousExamInformationActivity) {
        interactor = new ExamOverviewInteractor();
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
