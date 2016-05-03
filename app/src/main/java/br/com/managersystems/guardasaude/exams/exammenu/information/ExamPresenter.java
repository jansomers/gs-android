package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.util.Log;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.ui.fragments.InformationFragment;

/**
 * Created by Jan on 26/04/2016.
 */
public class ExamPresenter implements IExamPresenter, OnInformationRetrievedListener {
    
    IExamInformationView informationFragment;
    ExamInteractor examInteractor;

    public ExamPresenter(InformationFragment informationFragment) {
        this.informationFragment = informationFragment;
        examInteractor = new ExamInteractor(this);
    }

    @Override
    public void retrieveInformation(Intent intent) {
        Log.d(getClass().getSimpleName(), "Received intent from view... Forwarding to interactor");
        examInteractor.getExam(intent);
    }



    @Override
    public void onFailure() {
        Log.d(getClass().getSimpleName(), "Received interactor failure... Alerting view!");
        informationFragment.showInformationError();

    }

    @Override
    public void onExamSuccess(Exam exam) {
        Log.d(getClass().getSimpleName(), "Received interactor success... Notifying view!");
        informationFragment.showInformation(exam);

    }
}
