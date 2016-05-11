package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.ExamList;

public interface OnCallExamListFinishedListener {
    /**
     * The examlist was successfully retrieved and handed over to the preseneter
     * @param examList: the list of exams for one user
     */
    void onSuccessGetExamList(ExamList examList);

    /**
     * The examlist was not successfully retrieved
     */
    void onFailureGetExamList();

    /**
     * The exam was successfully associated to the user and handed over to the presenter
     * @param associatedExamResponse
     */
    void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse);

    /**
     * The exam could not be associated to the user
     */
    void onFailureFindNewExam();
}
