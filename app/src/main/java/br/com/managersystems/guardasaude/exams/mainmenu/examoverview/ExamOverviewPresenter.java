package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import android.content.SharedPreferences;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.ExamInteractor;
import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.ui.fragments.ExamOverviewFragment;
import br.com.managersystems.guardasaude.util.Base64Interactor;


public class ExamOverviewPresenter implements IExamOverviewPresenter, OnCallExamListFinishedListener {
    ExamInteractor examOverviewInteractor;
    ExamOverviewFragment examOverview;
    Base64Interactor base64Interactor;
    SharedPreferences sp;


    public ExamOverviewPresenter(ExamOverviewFragment examOverview,SharedPreferences sharedPreferences) {
        this.examOverview = examOverview;
        this.sp = sharedPreferences;
        base64Interactor = new Base64Interactor();
        examOverviewInteractor = new ExamInteractor();
    }

    @Override
    public void getSortedExamList(String sortBy, String orderBy){
        byte [] encryptedUser =  sp.getString("user",null).getBytes();
        String user= base64Interactor.decodeBase64ToString(encryptedUser);
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);
        examOverviewInteractor.getExamList(this,user,token,orderBy,sortBy,null,null,null,role);
    }

    @Override
    public void findNewExam(String exid, String ePassCode) {
        byte [] encryptedUser =  sp.getString("user",null).getBytes();
        String user= base64Interactor.decodeBase64ToString(encryptedUser);
        String token = sp.getString("token",null);
        examOverviewInteractor.associateNewExam(this,user,token,exid,ePassCode);
    }

    @Override
    public void onSuccessGetExamList(ExamList examList) {
        examOverview.onSuccessExamList(
                (ArrayList<Exam>) examList.getRows());
    }

    @Override
    public void onFailureGetExamList() {
        examOverview.onFailureExamList();
    }

    @Override
    public void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse) {
        examOverview.onSuccessFindNewExam(associatedExamResponse);
    }

    @Override
    public void onFailureFindNewExam() {
        examOverview.onFailureFindNewExam();
    }

}
