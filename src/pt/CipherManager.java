package pt;

public class CipherManager {
	private CipherType cipherType ;
	private int key1 = 7 ;
	public CipherManager() {}
	public CipherManager(CipherType ct) {
		cipherType = ct;
		//for testing purpose
		cipherType = CipherType.RailFence;
	}
	
	public String Encryption(String text) {
		String result = new String();
		
		switch (cipherType){
			case Affine:
			{
				result = AffineCipher.encryptionMessage(text,key1,5);
				break;
			}
			case Caesar:
			{
				result = CaesarCipher.encrypt(text,key1);
				break;
			}
			case RailFence:
			{
				result = RailFenceCipher.Encryption(text, key1);
				break;
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
				result = AffineCipher.decryptionMessage(text,key1,5);
				break;
			}
			case Caesar:
			{
				result = CaesarCipher.decrypt(text,key1);
				break;
			}
			case RailFence:
			{
				result = RailFenceCipher.Decryption(text, key1);
				break;
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
	

}
