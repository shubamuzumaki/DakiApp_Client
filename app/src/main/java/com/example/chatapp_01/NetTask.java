package com.example.chatapp_01;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class NetTask extends AsyncTask<String,Void,String>
{
    private Networkable currentActivity;

    public NetTask(Networkable currentActivity)
    {
        this.currentActivity = currentActivity;
    }

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            String request = params[0];
            System.out.println("Sending Request" + request);
            Socket client = new Socket(InetAddress.getByName(NetworkInfo.SERVER_IP),NetworkInfo.SERVER_PORT);
            client.setSoTimeout(5000);
            DataOutputStream dout = new DataOutputStream(client.getOutputStream());
            DataInputStream din = new DataInputStream(client.getInputStream());

            //connect to server
            dout.writeUTF(request);
            String response = din.readUTF();
            return response;
        }
        catch(Exception e)
        {
            System.out.println("Err in NetTask doinBackground : " + e);
            return Integer.toString(CommunicationFlags.CONNECTION_FAILED);
        }
    }

    @Override
    protected void onPostExecute(String response)
    {
        currentActivity.networkResponse(response);
    }
}

