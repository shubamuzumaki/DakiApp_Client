package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity implements Networkable
{
    String username;
    String userObjId;
    String friendId;
    TextView friendNameTv;
    EditText chatEditText;
    ListView chatListView;
    ArrayAdapter<String> chatListAdapter;
    ChatActivity currentActivity;
    ArrayList<String> messageList = new ArrayList<String>();
    Thread messagePollingThread;

    @Override
    public void networkResponse(String response)
    {
        int flag = ResponseProcessor.getResponseHeader(response);
        switch (flag)
        {
            case CommunicationFlags.GET_MESSAGES:
                String[] tempMessage = ResponseProcessor.getFriendList(response);
                System.err.println(response);
                if(tempMessage!=null)
                {
                    for(int i=0;i<tempMessage.length;++i)
                        messageList.add(friendId.toUpperCase()+": "+tempMessage[0]);
                    chatListAdapter.notifyDataSetChanged();
                }
                else
                {
                    System.err.println("No message to show...");
                }
                break;
        }
    }

    public void onClickSend(View view)
    {
        String chatMessage = chatEditText.getText().toString();
        System.out.println(username+"->"+friendId + ": " + chatMessage);
        String sendMsgRequest = RequestGenerator.getSendMessageResponse(userObjId,friendId,chatMessage);
        new NetTask(this).execute(sendMsgRequest);
        chatEditText.setText("");

        messageList.add("YOU: "+chatMessage);
        chatListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        currentActivity = this;

        //intent
        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("USERNAME");
        userObjId = (String)intent.getSerializableExtra("USER_OBJ_ID");
        friendId = (String)intent.getSerializableExtra("FRIEND_ID");
        friendId = friendId.toUpperCase();

        //adapter
        chatListAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,messageList);
        chatListView.setAdapter(chatListAdapter);

        friendNameTv.setText(friendId);

        messagePollingThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String getMessageRequest = RequestGenerator.getMessageRequest(userObjId,friendId);
                while(true)
                {
                    new NetTask(ChatActivity.this).execute(getMessageRequest);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (Exception e){}
                }
            }
        });
        messagePollingThread.start();
    }

    private void initViews()
    {
        friendNameTv  = findViewById(R.id.friendNameTv);
        chatEditText = findViewById(R.id.chatEditText);
        chatListView = findViewById(R.id.chatListView);
    }
}
