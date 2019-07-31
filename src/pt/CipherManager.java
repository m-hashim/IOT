package pt;

public class CipherManager {
	public static CipherManager Instance;
	
	private CipherType cipherType ;
	public CipherManager() {}
	public CipherManager(CipherType ct) {
		cipherType = ct;
	}
	
	public String Encryption(String text) {
		String result = new String();
		switch (cipherType){
			case Affine:
			{
				result = AffineCipher.encryptionMessage(text);
			}
			case Caesar:
			{
				result = CaesarCipher.encrypt(text,2);
			}
			case RailFence:
			{
				result = RailFenceCipher.Encryption(text, 5);
			}
			case Vigenere:
			{
				result = VigenereCipher.cipherText(text, VigenereCipher.generateKey(text, "Hashim"));
			}
			default:
			{
				result = text;
			}
		}
		return result;
	}
	public String Decryption(String text)	{
		String result = new String();
		switch (cipherType){
			case Affine:
			{
				result = AffineCipher.decryptionMessage(text);
			}
			case Caesar:
			{
				result = CaesarCipher.decrypt(text,2);
			}
			case RailFence:
			{
				result = RailFenceCipher.Decryption(text, 5);
			}
			case Vigenere:
			{
				result = VigenereCipher.originalText(text, VigenereCipher.generateKey(text, "Hashim"));
			}
			default:
			{
				result = text;
			}
		}
		return result;
	}
}
