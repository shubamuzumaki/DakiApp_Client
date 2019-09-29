package com.example.chatapp_01;

//generates different request required by the client to send to the server
public class RequestGenerator
{
    public static String getSignUpRequest(String username,String password)
    {
        StringBuffer request = new StringBuffer();
        request.append(CommunicationFlags.SIGN_UP)
               .append(CommunicationFlags.SEPARATOR_1)
               .append(username)
               .append(CommunicationFlags.SEPARATOR_2)
               .append(password);
        return request.toString();
    }

    public static String getLoginRequest(String username,String password)
    {
        StringBuffer request = new StringBuffer();
        request.append(CommunicationFlags.LOGIN)
                .append(CommunicationFlags.SEPARATOR_1)
                .append(username)
                .append(CommunicationFlags.SEPARATOR_2)
                .append(password);
        return request.toString();
    }

    public static String getFriendListRequest(String userObjId)
    {
        StringBuffer request = new StringBuffer();
        request.append(CommunicationFlags.GET_FRIEND_LIST)
               .append(CommunicationFlags.SEPARATOR_1)
               .append(userObjId);

        return request.toString();
    }

    public static String getAddFriendRequest(String userObjId, String friendId)
    {
        StringBuffer request = new StringBuffer();
        request.append(CommunicationFlags.ADD_FRIEND_REQUEST)
                .append(CommunicationFlags.SEPARATOR_1)
                .append(userObjId)
                .append(CommunicationFlags.SEPARATOR_2)
                .append(friendId);

        return request.toString();
    }

    public static String getSendMessageResponse(String userObjId, String friendId , String message)
    {
        StringBuffer request = new StringBuffer();
        request.append(CommunicationFlags.SEND_MSG)
                .append(CommunicationFlags.SEPARATOR_1)
                .append(userObjId)
                .append(CommunicationFlags.SEPARATOR_2)
                .append(friendId)
                .append(CommunicationFlags.SEPARATOR_3)
                .append(message);
        return request.toString();
    }

    static String getMessageRequest(String userObjId, String friendId)
    {
        StringBuffer request = new StringBuffer();
        request.append(CommunicationFlags.GET_MESSAGES)
                .append(CommunicationFlags.SEPARATOR_1)
                .append(userObjId)
                .append(CommunicationFlags.SEPARATOR_2)
                .append(friendId);

        return request.toString();
    }
}
