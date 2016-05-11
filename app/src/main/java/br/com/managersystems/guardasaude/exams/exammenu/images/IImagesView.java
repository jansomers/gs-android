package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.content.SharedPreferences;

public interface IImagesView {
    /**
     * Show error to user
     */
    void noImagesFound();

    /**
     * Initializes the gridview
     * Determines padding for the gridview
     * Sets number of columns in the gridview
     */
    void initializeGridLayout();

    /**
     * All images were successfully received
     * Set adapter to gridview and set the images to be shown
     */
    void imagesReceivedSucces();

    /**
     * Gets the width of the screen
     */
    int getScreenWidth();

    void setSharedPreferences(SharedPreferences sharedPreferences);

}
