package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity implements Networkable
{
    String username;
    String userObjId;
    String friendId;
    TextView friendNameTv;
    EditText chatEditText;

    @Override
    public void networkResponse(String response)
    {
        System.out.println("response from:" + response);
    }

    public void onClickSend(View view)
    {
        String chatMessage = chatEditText.getText().toString();
        System.out.println(username+"->"+friendId + ": " + chatMessage);
        chatEditText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();

        //intent
        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("USERNAME");
        userObjId = (String)intent.getSerializableExtra("USER_OBJ_ID");
        friendId = (String)intent.getSerializableExtra("FRIEND_ID");
        friendId = friendId.toUpperCase();

        friendNameTv.setText(friendId);
    }

    private void initViews()
    {
        friendNameTv  = findViewById(R.id.friendNameTv);
        chatEditText = findViewById(R.id.chatEditText);
    }
}
