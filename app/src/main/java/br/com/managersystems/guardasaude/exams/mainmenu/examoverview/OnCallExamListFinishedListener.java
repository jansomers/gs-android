package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.ExamList;

public interface OnCallExamListFinishedListener {
    /**
     * The exam list was successfully retrieved and handed over to the presenter
     * @param examList: the list of exams for one user
     */
    void onSuccessGetFirstExamList(ExamList examList);

    /**
     * The exam list was not successfully retrieved
     */
    void onFailureGetExamList();

    /**
     * The exam was successfully associated to the user and handed over to the presenter
     * @param associatedExamResponse Response object with the associated exam
     */
    void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse);

    /**
     * The exam could not be associated to the user
     */
    void onFailureFindNewExam();

    void onSuccessGetNextExamList(ExamList examList);
}
