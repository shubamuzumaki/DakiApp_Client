package com.example.chatapp_01;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class Authenticator extends AsyncTask<String,Void,Integer>
{
    MainActivity mainActivity;
    public Authenticator(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        String username = params[0];
        String password = params[1];
        int requestType = Integer.parseInt(params[2]);

        //connect to server to authenticate user
        try
        {
            System.out.println("Sending Request");
            Socket client = new Socket(InetAddress.getByName(NetworkInfo.SERVER_IP),NetworkInfo.SERVER_PORT);
            DataOutputStream dout = new DataOutputStream(client.getOutputStream());
            DataInputStream din = new DataInputStream(client.getInputStream());
            String request;

            switch (requestType)
            {
                case CommunicationFlags.SIGN_UP:
                    request = CommunicationFlags.SIGN_UP + CommunicationFlags.SEPARATOR_1;
                    break;
                case CommunicationFlags.LOGIN:
                    request = CommunicationFlags.LOGIN + CommunicationFlags.SEPARATOR_1;
                    break;
                default:
                    return CommunicationFlags.ERR_INVALID_ARGUMENTS;
            }

            request += username + CommunicationFlags.SEPARATOR_2 + password;

            //connect to server
            dout.writeUTF(request);
            String response = din.readUTF();
            return ResponseProcessor.getResponseHeader(response);
        }
        catch(Exception e)
        {
            System.out.println("Err in Authenticator doinBackground : " + e);
        }

        return 0;
    }

    @Override
    protected void onPostExecute(Integer flag)
    {
        switch(flag)
        {
            case CommunicationFlags.SIGN_UP_SUCCESSFULL:
                System.out.println("Signup SuccesFull");
                break;
            case CommunicationFlags.SIGN_UP_FAILED:
                System.out.println("SignUp Failed");
                break;
            case CommunicationFlags.LOGIN_SUCCESSFULL:
                System.out.println("Login Successfull");
                break;
            case CommunicationFlags.LOGIN_FAILED:
                System.out.println("Login Failed");
                break;
            default:
                System.out.println("Invalid Response");
                break;
        }
    }
}//inner class

