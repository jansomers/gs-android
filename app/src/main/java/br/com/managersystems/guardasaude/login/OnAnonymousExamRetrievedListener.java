package br.com.managersystems.guardasaude.login;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface OnAnonymousExamRetrievedListener {
    /**
     * Calls anonymousExamSucces method from activity and passes exam
     */
    void examRetrievedSucces(Exam exam);

    /**
     * Calls anonymousExamFailure method from activity
     */
    void examRetrievedFailure();
}
