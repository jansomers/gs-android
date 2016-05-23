package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.content.SharedPreferences;

public interface IImagesView {
    /**
     * Shows an error to the user when no images are found.
     */
    void noImagesFound();

    /**
     * Initializes the gridview.
     * Determines padding for the gridview.
     * Sets number of columns in the gridview.
     */
    void initializeGridLayout();

    /**
     * TODO rename method (not a listener) --> showImages ?
     * Sets the adapter for the gridview and sets the images to be shown if all images were received.
     */
    void imagesReceivedSuccess();

    /**
     * Gets the width of the screen.
     */
    int getScreenWidth();

    void setSharedPreferences(SharedPreferences sharedPreferences);

}
