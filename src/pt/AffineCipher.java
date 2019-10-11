package pt;

public class AffineCipher
{
    public static String encryptionMessage(String Msg,int key1,int key2)
    {
    	String CTxt = "";
    	try {
	        int a = key1;
	        int b = key2;
	        
	        for (int i = 0; i < Msg.length(); i++)
	        {
	        	CTxt = CTxt + (char)((a*((int)Msg.charAt(i)-32)+b)%96+32) ;
	        /*
	        	System.out.print((int)Msg.charAt(i)+"\t");
	        	System.out.print((int)Msg.charAt(i)-32+"\t");
	        	System.out.print(a*((int)Msg.charAt(i)-32)+"\t");
	        	System.out.print(a*((int)Msg.charAt(i)-32)+b+"\t");
	        	System.out.print((a*((int)Msg.charAt(i)-32)+b)%96+"\t");

	        	System.out.print((a*((int)Msg.charAt(i)-32)+b)%96+32+"\t");
	        	System.out.println();
	        */
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
	        int a = key1;
	        int b = key2;
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
	        	int val = ((a_inv *((int)CTxt.charAt(i) - b) % 96)-32 )%96 ;
	            val = val<96?val+32:val;
	            val = val<32?val+96:val;
	        	Msg = Msg + (char) (val);
	            /*
	            System.out.println("A inverse " + a_inv);
	            System.out.print((int)CTxt.charAt(i)+"\t");
	            System.out.print((int)CTxt.charAt(i)+32 +"\t");
	            System.out.print((int)CTxt.charAt(i)+32- b+"\t");
	            System.out.print(a_inv *((int)CTxt.charAt(i)+32 - b) +"\t");
	            System.out.print((a_inv *((int)CTxt.charAt(i) - b) % 96) +"\t");
	            System.out.print((((a_inv *((int)CTxt.charAt(i) - b) % 96)-32 )%96 )+"\t");
	            
	            System.out.println();
	            */
	        }
        }catch(Exception e) {
			System.out.println("Error in Decryption");
			e.printStackTrace();
		}finally {
			//System.out.println("Error in Encrytion a and b are "+key1+" "+key2);
		}
        return Msg;
    }
    public static void main(String[] args) {
    	System.out.println(AffineCipher.decryptionMessage(AffineCipher.encryptionMessage("00.00", 13, 11),13,11));
    }
    
}
