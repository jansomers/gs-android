package br.com.managersystems.guardasaude.util;

import android.util.Base64;

public class Base64Interactor implements IBase64Interactor{

    @Override
    public String encodeStringToBase64(String decodedString) {
        byte[] data = decodedString.getBytes();
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return  base64;
    }

    @Override
    public String decodeBase64ToString(byte[] base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        String decodedString = new String(data);
        return decodedString;
    }
}
