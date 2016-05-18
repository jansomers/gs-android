package br.com.managersystems.guardasaude.exams.exammenu.information;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface OnAnonymousExamRetrievedListener {
    /**
     * TODO rename view method (view is not a "listener")
     * Calls anonymousExamSucces method from activity and passes exam
     */
    void examRetrievedSucces(Exam exam);

    /**
     * TODO rename view method (view is not a "listener")
     * Calls anonymousExamFailure method from activity
     */
    void examRetrievedFailure();
}
