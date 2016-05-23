package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

public interface IExamOverviewPresenter  {
    /**
     * The first time the exam list is loaded, before pagination
     * Gets user, token and role from shared preferences
     * addresses the examOverviewInteractor
     * @param sortBy : patient, identification or date
     * @param orderBy : asc or desc
     * @param isEmergency
     */
    void getFirstSortedExamList(String sortBy, String orderBy, String isEmergency);

    /**
     * The first time the exam list is loaded, before pagination
     * Gets user, token and role from shared preferences
     * addresses the examOverviewInteractor
     * @param sortBy: patient, identification or date
     * @param orderBy: asc or desc
     */
    void getNextSortedExamList(String sortBy, String orderBy, String offsetValue);

    /**
     * Gets user and token from shared preferences
     * addresses the examOverviewInteractor
     * @param exid: identification of the exam
     * @param ePassCode: accesscode or password of the exam
     */
    void findNewExam(final String exid, final String ePassCode);

    void getFilteredExamList(String filterBy);
}
