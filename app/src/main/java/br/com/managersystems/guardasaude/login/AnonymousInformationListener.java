package br.com.managersystems.guardasaude.login;


import br.com.managersystems.guardasaude.exams.domain.Exam;

public interface AnonymousInformationListener {
    void onExamReceived(Exam exam);
    void onExamFailure();
}
