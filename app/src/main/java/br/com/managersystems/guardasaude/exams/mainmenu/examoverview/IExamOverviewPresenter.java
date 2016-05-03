package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

public interface IExamOverviewPresenter  {
    void getExamList();
    void getSortedExamList(String sortBy, String orderBy);
    void findNewExam(final String exid, final String ePassCode);
}
