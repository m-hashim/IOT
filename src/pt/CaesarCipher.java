package pt;

/**
 * Methods to encrypt and decript Strings using a Caesar Cipher. A Caesar Cipher
 * is an encryption method that operates by shifting letters of your message a
 * certain number of characters forwards or backwards. It is not a very secure
 * encyption technique, as the frequency of letters present in the English
 * language can easily be used to discover the key used to encrypt the message.
 *
 * @author  Thomas Coe
 * @version 1.0 2013-10-17
 */
public class CaesarCipher {

    /**
     * Encrypts a message using a Caesar Cipher with a given shift. Assumes the
     * String only contains characters defined in the standard ASCII table from
     * 32-126
     *
     * @param original The original String to encrypt
     * @param shift    An int containing the shift to use for the cipher
     * @return         A String containing the encrypted message
     */
    public static String encrypt(String original, int shift) {
        String encrypted = "";
        for (int i = 0; i < original.length(); i++) {
            int c = original.charAt(i) + shift;
            if (c > 126) {
                c -= 95;
            } else if (c < 32) {
                c += 95;
            }
            encrypted += (char) c;
        }
        return encrypted;
    }

    /**
     * Decrypts a message that was encoded with a Caesar Cipher. Assumes the
     * String encypted only used standard ASCII characters of values 32-126
     *
     * @param encrypted The encrypted String to decrypt
     * @param shift     An int containing the shift that was use to encrypt the
     *                  original String
     * @return          A String containing the decrypted message
     */
    public static String decrypt(String encrypted, int shift) {
        return encrypt(encrypted, -shift);
    }
}