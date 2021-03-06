package pt;
class RailFenceCipher{
	int depth;
	static String Encryption(String plainText,int depth)
	{
		String cipherText="";
		try {
			int r=depth,len=plainText.length();
			int c=len/depth;
			char mat[][]=new char[r][c];
			int k=0;
			for(int i=0;i< c;i++)
			    {
			    for(int j=0;j< r;j++)
				    {
				    if(k!=len)
				    	mat[j][i]=plainText.charAt(k++);
				    else
				    	mat[j][i]='X';
				    }
			    }
			  	for(int i=0;i< r;i++)
			  		{
			  		for(int j=0;j< c;j++)
			  			{
			  			cipherText+=mat[i][j];
			  			}
			  		}
			}
		catch(Exception e) {
			System.out.println("Error in Encryption");
			e.printStackTrace();
		}
		return cipherText;
	}
	  
	  
	static String Decryption(String cipherText,int depth)
	{	
		String plainText="";
		try {
			int r=depth,len=cipherText.length();
			int c=len/depth;
			char mat[][]=new char[r][c];
			int k=0;
		   
			
		   
			for(int i=0;i< r;i++)
				{
				for(int j=0;j< c;j++)
					{
					mat[i][j]=cipherText.charAt(k++);
					}
				}
			for(int i=0;i< c;i++)
				{
				for(int j=0;j< r;j++)
					{
					plainText+=mat[j][i];
					}
				}
		}catch(Exception e) {
			System.out.println("Error in Decryption");
			e.printStackTrace();
		}
		return plainText;
	}
}
 
