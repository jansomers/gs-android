package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import android.content.Intent;

import br.com.managersystems.guardasaude.exams.exammenu.information.onAnonymousInformationRetrievedListener;
import br.com.managersystems.guardasaude.exams.exammenu.information.onAnonymousExamRetrievedListener;

public interface IExamListInteractor {
    /**
     * initiates Retrofit for a given api
     */
    ExamApi initiateRetrofit();

    /**
     * Makes a call to the examApi to retrieve the list of exams for one user for the first page
     */
    void getFirstExamList(final OnCallExamListFinishedListener listener, final String userName, final String token,final String orderBy,final String sortBy, final String maxValue, final String offsetValue, final String filterBy, final String accesRole);

    /**
     * Makes a call to the examApi to retrieve the list of exams for one user for pagination
     */
    void getNextExamList(final OnCallExamListFinishedListener listener, final String userName, final String token,final String orderBy,final String sortBy, final String maxValue, final String offsetValue, final String filterBy, final String accesRole);

    /**
     * Makes a call to the examApi to associate an exam to one user
     * @param ePassCode: the access code or password for the exam
     * @param exid: the identification code of the exam
     */
    void associateNewExam(final OnCallExamListFinishedListener listener,final String user,final String token, final String exid, final String ePassCode);

    /**
     *Makes a call to the examApi to get an anonymous exam
     * An anonymous exam means that user is requesting an exam without an account
     * @param accessCodeString: the access code or password for the exam
     * @param examIdString: the identification code of the exam
     */
    void getAnonymousExam(onAnonymousExamRetrievedListener listener,String accessCodeString, String examIdString);

    /**
     * Gets the exam from the intent
     * If exam is empty call onExamFailure method
     * If exam is not empty call onExamReceived method and pass exam
     */
    void getExam(final onAnonymousInformationRetrievedListener listener,Intent intent);
}
