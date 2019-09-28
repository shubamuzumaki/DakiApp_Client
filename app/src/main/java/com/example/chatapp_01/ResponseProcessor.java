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

    public static String getUserObjectId(String response)
    {
        try
        {
            return response.split(CommunicationFlags.SEPARATOR_1)[1];
        }
        catch(ArrayIndexOutOfBoundsException aiobe)
        {
            System.out.println("Err in response processor - getUserObjId: "+ aiobe);
            return Integer.toString(CommunicationFlags.ERR_INVALID_ARGUMENTS);
        }
    }

    public static String[] getFriendList(String response)
    {
        String[] friendList = null;
        try
        {
            String listString = response.split(CommunicationFlags.SEPARATOR_1)[1];
            friendList = listString.split(CommunicationFlags.SEPARATOR_FRIEND_LIST);
            return friendList;
        }
        catch(ArrayIndexOutOfBoundsException aiobe)
        {
            System.out.println("Err in response processor - getFriend List: "+ aiobe);
            return friendList;
        }
    }

    public static String getFriendId(String response)
    {
        try
        {
            return  response.split(CommunicationFlags.SEPARATOR_1)[1];
        }
        catch(ArrayIndexOutOfBoundsException aiobe)
        {
            System.out.println("Err in response processor - getFriend Id: "+ aiobe);
            return null;
        }
    }
}
