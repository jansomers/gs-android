package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.content.SharedPreferences;

public interface IImagesView {
    void setSharedPreferences(SharedPreferences sharedPreferences);
    void noImagesFound();
}
