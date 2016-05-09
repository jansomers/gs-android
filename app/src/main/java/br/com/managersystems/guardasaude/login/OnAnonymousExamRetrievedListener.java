package br.com.managersystems.guardasaude.login;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface OnAnonymousExamRetrievedListener {
    void examRetrievedSucces(Exam exam);
    void examRetrievedFailure();
}
