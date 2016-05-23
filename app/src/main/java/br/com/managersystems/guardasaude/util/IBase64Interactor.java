package br.com.managersystems.guardasaude.util;

/**
 * Utility class that handles and/or returns base64 encoded objects.
 * Currently handles:
 * - Encoding a string to a base64 string.
 * - Decoding a base64 byte stream to a string.
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Also see:
 * @see IBase64Interactor
 */
public interface IBase64Interactor {

    /**
     * Encodes a string to base 64.
     * @param decodedString String object that represents the text to encode.
     * @return String object that represents the encoded base64.
     */
    String encodeStringToBase64(String decodedString);

    /**
     * Decodes a string from base64.
     * @param encodedBytes Byte stream that represent the base64.
     * @return String object that represents the decoded string.
     */
    String decodeBase64ToString(byte[] encodedBytes);
}
