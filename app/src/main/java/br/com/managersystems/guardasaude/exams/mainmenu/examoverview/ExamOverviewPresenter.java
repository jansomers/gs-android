package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import android.content.SharedPreferences;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.ui.fragments.ExamOverviewFragment;
import br.com.managersystems.guardasaude.util.Base64Interactor;


public class ExamOverviewPresenter implements IExamOverviewPresenter, OnCallExamListFinishedListener {
    ExamOverviewInteractor examOverviewInteractor;
    ExamOverviewFragment examOverview;
    Base64Interactor base64Interactor;
    SharedPreferences sp;
    private final String START_MAX_VALUE="5";
    private final String START_OFFSET_VALUE="0";

    public ExamOverviewPresenter(ExamOverviewFragment examOverview,SharedPreferences sharedPreferences) {
        this.examOverview = examOverview;
        this.sp = sharedPreferences;
        base64Interactor = new Base64Interactor();
        examOverviewInteractor = new ExamOverviewInteractor();
    }

    @Override
    public void getFirstSortedExamList(String sortBy, String orderBy, String isEmergency){
        String user=base64Interactor.decodeBase64ToString(sp.getString("user", "").getBytes());
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);
        examOverviewInteractor.getFirstExamList(this, user, token, orderBy, sortBy, START_MAX_VALUE,START_OFFSET_VALUE, null, role,isEmergency);
    }

    @Override
    public void getNextSortedExamList(String sortBy, String orderBy, String offsetValue) {
        String user= base64Interactor.decodeBase64ToString(sp.getString("user", "").getBytes());
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);
        examOverviewInteractor.getNextExamList(this,user,token,orderBy,sortBy,START_MAX_VALUE,offsetValue+5,null,role);
    }

    @Override
    public void findNewExam(String exId, String ePassCode) {
        String user= base64Interactor.decodeBase64ToString(sp.getString("user", "").getBytes());
        String token = sp.getString("token",null);
        examOverviewInteractor.associateNewExam(this,user,token,exId,ePassCode);
    }

    @Override
    public void getFilteredExamList(String filterBy) {
        String user= base64Interactor.decodeBase64ToString(sp.getString("user", "").getBytes());
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);
        examOverviewInteractor.getFirstExamList(this,user,token,null,null,START_MAX_VALUE,START_OFFSET_VALUE,filterBy,role,"false");
    }

    @Override
    public void onSuccessGetExamList(ExamList examList) {
        examOverview.showExamList((ArrayList<Exam>) examList.getRows());
    }

    @Override
    public void onFailureGetExamList() {
        examOverview.showLoadingExamsError();
    }

    @Override
    public void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse) {
        examOverview.onSuccessFindNewExam(associatedExamResponse);
    }

    @Override
    public void onFailureFindNewExam() {
        examOverview.showInternalFailForNewExam();
    }

    @Override
    public void onSuccessGetNextExamList(ExamList examList) {
        examOverview.showNextExamList((ArrayList<Exam>) examList.getRows());
    }

}
