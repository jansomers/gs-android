package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import android.content.SharedPreferences;
import android.widget.Toast;

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


    public ExamOverviewPresenter(ExamOverviewFragment examOverview,SharedPreferences sharedPreferences) {
        this.examOverview = examOverview;
        this.sp = sharedPreferences;
        base64Interactor = new Base64Interactor();
        examOverviewInteractor = new ExamOverviewInteractor();
    }

    @Override
    public void getExamList() {
        byte [] encryptedUser =  sp.getString("user",null).getBytes();
        String user= base64Interactor.decodeBase64ToString(encryptedUser);
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);
        examOverviewInteractor.getExamList(this,user,token,null,null,null,null,null,role);
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
        ArrayList<Exam> exams = (ArrayList<Exam>) examList.getRows();
        examOverview.onSuccessExamList(exams);
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
