package br.com.managersystems.guardasaude.util;

/**
 * Created by Jan on 19/04/2016.
 */
public interface IBase64Interactor {

    String encodeStringToBase64(String decodedString);
    String decodeBase64ToString(byte[] encodedBytes);
}
