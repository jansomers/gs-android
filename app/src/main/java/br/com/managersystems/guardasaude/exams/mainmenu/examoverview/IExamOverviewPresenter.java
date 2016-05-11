package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

public interface IExamOverviewPresenter  {
    /**
     * Gets user, taken and role from shared preferences
     * addresses the examOverviewInteractor
     * @param sortBy: patient, identification or date
     * @param orderBy: asc or desc
     */
    void getSortedExamList(String sortBy, String orderBy);

    /**
     * Gets user and token from shared preferences
     * addresses the examOverviewInteractor
     * @param exid: identification of the exam
     * @param ePassCode: accesscode or password of the exam
     */
    void findNewExam(final String exid, final String ePassCode);
}
