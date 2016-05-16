package br.com.managersystems.guardasaude.exams.exammenu.information;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface onAnonymousInformationRetrievedListener {
    /**
     * Calls setExamInformation method from fragment and passes exam
     */
    void onExamReceived(Exam exam);

    /**
     * Calls examNotFound method from fragment
     */
    void onExamFailure();
}
