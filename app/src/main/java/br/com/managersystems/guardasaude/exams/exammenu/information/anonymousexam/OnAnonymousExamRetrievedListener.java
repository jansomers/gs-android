package br.com.managersystems.guardasaude.exams.exammenu.information.anonymousexam;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface OnAnonymousExamRetrievedListener {
    /**
     * TODO rename view method (view is not a "listener")
     * Calls anonymousExamSuccess method from activity and passes exam
     */
    void examRetrievedSuccess(Exam exam);

    /**
     * TODO rename view method (view is not a "listener")
     * Calls anonymousExamFailure method from activity
     */
    void examRetrievedFailure();
}
