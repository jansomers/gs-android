package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Jan on 26/04/2016.
 */
public interface IExamPresenter {
    void retrieveInformation(Intent intent);
    void retrieveComments(CharSequence exid, SharedPreferences sp);
}
