package com.example.chatapp_01;
import java.net.*;
import java.io.*;

//this class Store Info about user it's Userame and Password
//.) provide facility to authenticate user over network from the server
public class User implements Runnable
{
    private String username;
    private String password;
    MainActivity mainActivity;
    Thread thread;

    public User(MainActivity mainActivity,String userName , String password)
    {
        this.username = userName;
        this.password = password;
        thread  = new Thread(this);
        this.mainActivity = mainActivity;
    }

    public void login() throws IllegalArgumentException
    {

    }

    public void signup() throws IllegalArgumentException
    {
       thread.start();
    }

    @Override
    public void run()
    {
        //make connection with the server
        try
        {
            System.out.println("Signing up... with "+NetworkInfo.SERVER_IP+":"+NetworkInfo.SERVER_PORT);
            Socket  client = new Socket(InetAddress.getByName(NetworkInfo.SERVER_IP),NetworkInfo.SERVER_PORT);

            DataInputStream din = new DataInputStream(client.getInputStream());
            DataOutputStream dout = new DataOutputStream(client.getOutputStream());

            String request = Integer.toString(CommunicationFlags.SIGN_UP);

            dout.writeUTF(request);
            //send username and password
            dout.writeUTF(username);
            dout.writeUTF(password);

            String response = din.readUTF();
            //@todo create response Processor
            int ch = Integer.parseInt(response);
            mainActivity.onSignupLoginResponse(ch);

//            switch (ch)
//            {
//                case CommunicationFlags.SIGN_UP_SUCCESSFULL:
//                    System.out.println("SignUP SUCCESSFULL");
//                    break;
//                case CommunicationFlags.SIGN_UP_FAILED:
//                    System.out.println("SignUP FAILED");
//                    break;
//            }
        }//try
        catch(NullPointerException npe)
        {
            System.out.println("Null err in user login"+npe.getMessage());
        }
        catch(Exception e)
        {
            System.out.println("err in login:user: " + e);
//            e.printStackTrace();
        }
    }
}
