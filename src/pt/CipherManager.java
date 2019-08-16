package pt;

public class CipherManager {
	public static CipherManager Instance;
	public long totalEncryptionTime,totalDecryptionTime;
	public long totalEncryptionMemory,totalDecryptionMemory;
	Runtime rt;
	private CipherType cipherType ;
	public CipherManager() {}
	public CipherManager(CipherType ct) {
		cipherType = ct;
		totalEncryptionTime = 0;
		totalDecryptionTime = 0;
		totalEncryptionMemory = 0;
		totalDecryptionMemory = 0;
		rt = Runtime.getRuntime();	
	}
	
	public String Encryption(String text) {
		long startTime = System.nanoTime();
		long startMemory = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
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
		totalEncryptionMemory += ((rt.totalMemory() - rt.freeMemory()) / 1024 / 1024)-startMemory;
		totalEncryptionTime += (System.nanoTime()-startTime)/1000000;
		return result;
	}
	public String Decryption(String text)	{
		long startTime = System.nanoTime();
		long startMemory = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
		
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
		totalDecryptionMemory += ((rt.totalMemory() - rt.freeMemory()) / 1024 / 1024)-startMemory;
		totalDecryptionTime += (System.nanoTime()-startTime)/1000000;
		
		return result;
	}
}
