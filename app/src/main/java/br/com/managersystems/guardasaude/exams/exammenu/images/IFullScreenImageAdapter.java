package br.com.managersystems.guardasaude.exams.exammenu.images;


import java.io.IOException;

public interface IFullScreenImageAdapter {
    /**
     * Converts Uris in bitmaps
     * Adds each bitmap to images array
     * @throws IOException: if bitmap is not found
     */
    void init() throws IOException;
}
