package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import android.content.SharedPreferences;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.ui.fragments.ExamOverviewFragment;
import br.com.managersystems.guardasaude.util.Base64Interactor;


public class ExamOverviewPresenter implements IExamOverviewPresenter, OnCallExamListFinishedListener {
    ExamListInteractor examListInteractor;
    ExamOverviewFragment examOverview;
    Base64Interactor base64Interactor;
    SharedPreferences sp;


    public ExamOverviewPresenter(ExamOverviewFragment examOverview,SharedPreferences sharedPreferences) {
        this.examOverview = examOverview;
        this.sp = sharedPreferences;
        base64Interactor = new Base64Interactor();
        examListInteractor = new ExamListInteractor();
    }

    @Override
    public void getExamList() {
        byte [] encryptedUser =  sp.getString("user",null).getBytes();
        String user= base64Interactor.decodeBase64ToString(encryptedUser);
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);
        examListInteractor.getExamList(this,user,token,null,null,null,null,null,role);
    }

    @Override
    public void getSortedExamList(String sortBy, String orderBy){
        byte [] encryptedUser =  sp.getString("user",null).getBytes();
        String user= base64Interactor.decodeBase64ToString(encryptedUser);
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);
        examListInteractor.getExamList(this,user,token,orderBy,sortBy,null,null,null,role);
    }

    @Override
    public void onSuccess(ExamList examList) {
        ArrayList<Exam> exams = (ArrayList<Exam>) examList.getRows();
        examOverview.onSuccess(exams);
    }

    @Override
    public void onFailure() {
        examOverview.onFailure();
    }

}
