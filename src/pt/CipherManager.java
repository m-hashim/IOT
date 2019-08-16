package pt;

public class CipherManager {
	private CipherType cipherType ;
	private int key1 = 5 , key2 = 7;
	public CipherManager() {}
	public CipherManager(CipherType ct) {
		cipherType = ct;
		
	}
	
	public String Encryption(String text) {
		String result = new String();
		switch (cipherType){
			case Affine:
			{
				result = AffineCipher.encryptionMessage(text,key1,key2);
			}
			case Caesar:
			{
				result = CaesarCipher.encrypt(text,key1);
			}
			case RailFence:
			{
				result = RailFenceCipher.Encryption(text, key1);
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
				result = AffineCipher.decryptionMessage(text,key1,key2);
			}
			case Caesar:
			{
				result = CaesarCipher.decrypt(text,key1);
			}
			case RailFence:
			{
				result = RailFenceCipher.Decryption(text, key1);
			}
			default:
			{
				result = text;
			}
		}	
		return result;
	}
	
	public void SetKey1(int key) {
		key1 = key;
	}
	
	public void SetKey2(int key) {
		key2 = key;
	}
}
