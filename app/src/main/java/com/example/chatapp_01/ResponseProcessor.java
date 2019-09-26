package com.example.chatapp_01;

//This Class Processes responses send from the server
public class ResponseProcessor
{
    public static int getResponseHeader(String response)
    {
        try
        {
            String header = response.split(CommunicationFlags.SEPARATOR_1)[0];
            return Integer.parseInt(header);
        }
        catch(ArrayIndexOutOfBoundsException aiobe)
        {
            System.out.println("Err in response processor - getResponseHeader: "+ aiobe);
            return CommunicationFlags.ERR_INVALID_ARGUMENTS;
        }
    }
}
