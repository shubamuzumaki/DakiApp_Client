package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomePage extends AppCompatActivity implements Networkable
{
    @Override
    public void networkResponse(String response)
    {
        System.out.println("Friend List");
        System.out.println(response);
    }

    TextView usernameTextView;
    TextView friendListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        String username = (String)intent.getSerializableExtra("USERNAME");
        usernameTextView = (TextView) findViewById(R.id.usernameTextViewHomePage);
        friendListTextView = findViewById(R.id.friendListTextView);
        usernameTextView.setText(username);

        String getFriendListRequest  = Integer.toString(CommunicationFlags.GET_FRIEND_LIST);
    }
}
