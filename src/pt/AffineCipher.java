package pt;

public class AffineCipher
{
    public static String encryptionMessage(String Msg,int key1,int key2)
    {
    	String CTxt = "";
    	try {
	        int a = 7;
	        int b = key1;
	        
	        for (int i = 0; i < Msg.length(); i++)
	        {
	        	CTxt = CTxt + (char) ((((a * Msg.charAt(i)) + b) % 96)+32);
	        }
    	}catch(Exception e) {
			System.out.println("Error in Encrytion");
			e.printStackTrace();
		}finally {
			//System.out.println("Error in Encrytion a and b are "+key1+" "+key2);
		}
        return CTxt;
    }

    public static String decryptionMessage(String CTxt,int key1,int key2)
    {
        String Msg = "";
        try {
	        int a = 7;
	        int b = key1;
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
	            Msg = Msg + (char) ((a_inv * (CTxt.charAt(i) - b)% 96 )+32+32 );
	        }
        }catch(Exception e) {
			System.out.println("Error in Decryption");
			e.printStackTrace();
		}finally {
			//System.out.println("Error in Encrytion a and b are "+key1+" "+key2);
		}
        return Msg;
    }
}