package br.com.managersystems.guardasaude.login;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface AnonymousInformationListener {
    /**
     * Calls setExamInformation method from fragment and passes exam
     */
    void onExamReceived(Exam exam);

    /**
     * Calls examNotFound method from fragment
     */
    void onExamFailure();
}
