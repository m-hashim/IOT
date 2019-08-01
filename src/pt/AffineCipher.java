package pt;

public class AffineCipher
{
    public static String encryptionMessage(String Msg)
    {
    	String CTxt = "";
    	try {
	        int a = 5;
	        int b = 7;
	        for (int i = 0; i < Msg.length(); i++)
	        {
	        	CTxt = CTxt + (char) ((((a * Msg.charAt(i)) + b) % 96)+32);
	        }
    	}catch(Exception e) {
			System.out.println("Error in Encrytion");
			e.printStackTrace();
		}
        return CTxt;
    }

    public static String decryptionMessage(String CTxt)
    {
        String Msg = "";
        try {
	        int a = 5;
	        int b = 7;
	        int a_inv = 0;
	        int flag = 0;
	        for (int i = 0; i < 96; i++)
	        {
	            flag = (a * i) % 96;
	            if (flag == 1)
	            {
	                a_inv = i;
	            }
	        }
	        for (int i = 0; i < CTxt.length(); i++)
	        {
	            Msg = Msg + (char) (((a_inv * ((CTxt.charAt(i) - b))% 96 ))+32 );
	        }
        }catch(Exception e) {
			System.out.println("Error in Decryption");
			e.printStackTrace();
		}
        return Msg;
    }
}