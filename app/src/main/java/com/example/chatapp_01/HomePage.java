package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class HomePage extends AppCompatActivity implements Networkable
{
    String username;
    String userObjId;
    ListView contactListView;
    TextView noFriendPopUpTextView;
    ArrayAdapter<String> contactListAdapter;

    @Override
    public void networkResponse(String response)
    {
        System.out.println("Friend List");
        int flag = ResponseProcessor.getResponseHeader(response);
        switch (flag)
        {
            case CommunicationFlags.FRIEND_LIST:
                String[] friendList = ResponseProcessor.getFriendList(response);
                if(friendList != null)
                {
                    noFriendPopUpTextView.setText("");
                    ArrayList<String> contacts = new ArrayList<>(Arrays.asList(friendList));
                    contactListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contacts);
                    contactListView.setAdapter(contactListAdapter);
                }
                else
                {
                    noFriendPopUpTextView.setText("No Friends!!!\n Click AddFriend to Connect to Your Friends Today");
                }
                break;
            default:
                System.out.println("invalid response in Homepage: " + response);
                break;
        }
    }

    public void onClickAddFriend(View view)
    {
        System.out.println("Wants To add a Friend");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        contactListView = findViewById(R.id.contactListView);
        noFriendPopUpTextView = findViewById(R.id.noFreindPopUpTextView);

        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("USERNAME");
        userObjId = (String)intent.getSerializableExtra("USER_OBJ_ID");


        String getFriendListRequest  = Integer.toString(CommunicationFlags.GET_FRIEND_LIST) + CommunicationFlags.SEPARATOR_1 +userObjId;
        new NetTask(this).execute(getFriendListRequest);

//        testFunction();
    }

    void testFunction()
    {
        ArrayList<String> contacts = new ArrayList<>();
        contacts.add("Shubam");
        contacts.add("mall");

        contactListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,contacts);
        contactListView.setAdapter(contactListAdapter);
    }
}
