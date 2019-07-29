package pt;

import java.util.Scanner;
public class AffineCipher

{

    public static String encryptionMessage(String Msg)

    {

        String CTxt = "";

        int a = 5;

        int b = 7;

        for (int i = 0; i < Msg.length(); i++)

        {
        	// why 96 is need to be multiple of 32
        	CTxt = CTxt + (char) ((((a * Msg.charAt(i)) + b) % 96)+32);

        }

        return CTxt;

    }

 

    public static String decryptionMessage(String CTxt)

    {

        String Msg = "";

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

              //  System.out.println(i);

            }

        }

        for (int i = 0; i < CTxt.length(); i++)

        {

            Msg = Msg + (char) (((a_inv * ((CTxt.charAt(i) - b))% 96 ))+32 );

        }

        return Msg;

    }

 

    public static void main(String[] args)

    {

//        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the message: ");

  //      String message = sc.next();
        String message = "Message from Pub1 with MsgNo: 1";

        System.out.println("Message is :" + message);

        System.out.println("Encrypted Message is : "

                + encryptionMessage(message));

        System.out.println("Decrypted Message is: "

                + decryptionMessage(encryptionMessage(message)));

        //sc.close();

    }

}