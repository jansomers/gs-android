package br.com.managersystems.guardasaude.exams.exammenu.images;


import java.io.IOException;

public interface IFullScreenImageAdapter {
    /**
     * Converts URIS into Bitmaps.
     * Adds each bitmap to an array of image.
     * @throws IOException if the bitmap is not found.
     */
    void init() throws IOException;
}
