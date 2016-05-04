package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Jan on 26/04/2016.
 */
public interface IExamInteractor {
    void getExam(Intent intent);

    void getCommentsForExam(CharSequence exid, SharedPreferences sp);

    void postNewComment(CharSequence s, CharSequence exid, SharedPreferences sp);
}
