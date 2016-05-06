package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

public interface NewExamListener {
    /**
     * Retrieves the exam information from the dialog window redirects it to the presenter
     * @param identification: the identification code of the exam
     * @param accessCode: the accesscode or password for the exam
     */
    void onNewExamInformationRetrieved(String identification,String accessCode);
}
