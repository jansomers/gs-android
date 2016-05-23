package br.com.managersystems.guardasaude.util;

import android.util.Base64;

/**
 * Implementation of the IBase64Interactor.
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Also see:
 * @see IBase64Interactor
 */
public class Base64Interactor implements IBase64Interactor{

    @Override
    public String encodeStringToBase64(String decodedString) {
        byte[] data = decodedString.getBytes();
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    @Override
    public String decodeBase64ToString(byte[] base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return new String(data);
    }
}
