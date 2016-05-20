package br.com.managersystems.guardasaude.exams.exammenu.information.anonymousexam;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface OnAnonymousInformationRetrievedListener {
    /**
     * Calls setExamInformation method from fragment and passes exam
     */
    void onExamReceived(Exam exam);

    /**TODO rename view method (for instance showExamNotFound)
     * Calls examNotFound method from fragment
     */
    void onExamFailure();
}
