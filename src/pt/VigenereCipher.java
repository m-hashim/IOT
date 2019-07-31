package pt;

class VigenereCipher 
{
	static int alphabet = 128;
	static int ap = 32;

	static String generateKey(String str, String key) 
	{ 
		int x = str.length(); 
		for (int i = 0; ; i++) 
		{ 
			if (x == i) 
				i = 0; 
			if (key.length() == str.length()) 
				break; 
			key+=(key.charAt(i)); 
		} 
		return key; 
	} 

	static String cipherText(String str, String key) 
	{ 
		String cipher_text=""; 	
		try {
			for (int i = 0; i < str.length(); i++) 
			{ 
				int x = (str.charAt(i) + key.charAt(i)) %alphabet; 
				cipher_text+=(char)(x); 
			} 
		}catch(Exception e) {
			System.out.println("Error in Encrytion");
			e.printStackTrace();
		}
		return cipher_text; 
	} 

	static String originalText(String cipher_text, String key) 
	{ 
		String orig_text=""; 
		try {
			for (int i = 0 ; i < cipher_text.length() && i < key.length(); i++) 
			{ 
				int x = (cipher_text.charAt(i)   - key.charAt(i)  + alphabet) %alphabet; 
				orig_text+=(char)(x); 
			} 
		}catch(Exception e) {
			System.out.println("Error in Decryption");
			e.printStackTrace();
		}
		return orig_text; 
	} 
}
