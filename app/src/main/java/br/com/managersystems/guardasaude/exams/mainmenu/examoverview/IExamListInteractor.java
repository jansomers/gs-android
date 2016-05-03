package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


public interface IExamListInteractor {
    ExamApi initiateRetrofit();
    void getExamList(final OnCallExamListFinishedListener listener, final String userName, final String token,final String orderBy,final String sortBy, final String maxValue, final String offsetValue, final String filterBy, final String accesRole);
    void associateNewExam(final OnCallExamListFinishedListener listener,final String user,final String token, final String exid, final String ePassCode);
}
