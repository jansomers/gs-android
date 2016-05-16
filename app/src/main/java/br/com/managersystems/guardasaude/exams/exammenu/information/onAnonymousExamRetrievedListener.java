package br.com.managersystems.guardasaude.exams.exammenu.information;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface onAnonymousExamRetrievedListener {
    /**
     * Calls anonymousExamSucces method from activity and passes exam
     */
    void examRetrievedSucces(Exam exam);

    /**
     * Calls anonymousExamFailure method from activity
     */
    void examRetrievedFailure();
}
