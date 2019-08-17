package pt;

public class CaesarCipher {

	public static String encrypt(String original, int shift) {
        String encrypted = "";
        try {
	        for (int i = 0; i < original.length(); i++) {
	            int c = original.charAt(i) + shift;
	            if (c > 126) {
	                c -= 95;
	            } else if (c < 32) {
	                c += 95;
	            }
	            encrypted += (char) c;
	        }
        }catch(Exception e) {
			System.out.println("Error in Encrytion");
			e.printStackTrace();
		}
        return encrypted;
    }
    
	public static String decrypt(String encrypted, int shift) {
        return encrypt(encrypted, -shift);
    }
}