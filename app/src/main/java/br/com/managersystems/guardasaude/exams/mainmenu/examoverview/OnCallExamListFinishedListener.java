package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.ExamList;

public interface OnCallExamListFinishedListener {
    void onSuccessGetExamList(ExamList examList);
    void onFailureGetExamList();
    void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse);
    void onFailureFindNewExam();
}
