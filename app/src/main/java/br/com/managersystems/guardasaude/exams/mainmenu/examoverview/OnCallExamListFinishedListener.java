package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import br.com.managersystems.guardasaude.exams.domain.ExamList;

public interface OnCallExamListFinishedListener {
    void onSuccess(ExamList examList);
    void onFailure();
}
