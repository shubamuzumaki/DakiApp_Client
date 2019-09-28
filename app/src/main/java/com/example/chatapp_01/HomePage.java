package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class HomePage extends AppCompatActivity implements Networkable
{
    String username;
    String userObjId;
    ListView contactListView;
    TextView noFriendPopUpTextView;
    LinearLayout addFriendLl;
    TextView invalidfreindIdTv;
    EditText friendIdEt;
    ArrayAdapter<String> contactListAdapter;
    ArrayList<String> contacts;

    @Override
    public void networkResponse(String response)
    {
        System.out.println("Friend List");
        int flag = ResponseProcessor.getResponseHeader(response);
        switch (flag)
        {
//            -------------------------------------------------------------
            case CommunicationFlags.FRIEND_LIST:
                String[] friendList = ResponseProcessor.getFriendList(response);
                if(friendList != null)
                {
                    noFriendPopUpTextView.setText("");
                    contacts = new ArrayList<>(Arrays.asList(friendList));
                    contactListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contacts);
                    contactListView.setAdapter(contactListAdapter);
                }
                else
                {
                    noFriendPopUpTextView.setText("No Friends!!!\n Click AddFriend to Connect to Your Friends Today");
                }
                break;
//            -------------------------------------------------------------
            case CommunicationFlags.ADD_FRIEND_RESPONSE_SUCCESSFULL:
                System.out.println("Friend Added Successfully");
                contactListAdapter.add(ResponseProcessor.getFriendId(response));
                Toast.makeText(this,"Friend Added Successfully",Toast.LENGTH_SHORT).show();
                break;
//            -------------------------------------------------------------
            case CommunicationFlags.ADD_FRIEND_RESPONSE_FAILED:
                invalidfreindIdTv.setVisibility(View.VISIBLE);
                invalidfreindIdTv.setText("Please Enter a Valid Friend's Id");
                break;
//            -------------------------------------------------------------
            default:
                System.out.println("invalid response in Homepage: " + response);
                break;
        }
    }

    public void onClickAddFriend(View view)
    {
        System.out.println("Wants To add a Friend");
        addFriendLl.setVisibility(View.VISIBLE);
        invalidfreindIdTv.setVisibility(View.GONE);

    }

    public void onClickSubmitFriendId(View view)
    {
        invalidfreindIdTv.setVisibility(View.GONE);
        String friendId = friendIdEt.getText().toString();
        if(friendId.isEmpty())
        {
            invalidfreindIdTv.setVisibility(View.VISIBLE);
            invalidfreindIdTv.setText("Id can't be empty.Choose a Valid Id");
            return;
        }

        String addFriendRequest = RequestGenerator.getAddFriendRequest(userObjId,friendId);
        new NetTask(this).execute(addFriendRequest);
        System.out.println("Submitted Friend id: " + friendId);
    }

    public void onClickGoBack(View view)
    {
        System.out.println("Wants To Go Back");
        addFriendLl.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //cache
        contactListView = findViewById(R.id.contactListView);
        noFriendPopUpTextView = findViewById(R.id.noFreindPopUpTextView);
        addFriendLl = findViewById(R.id.addFriendLl);
        invalidfreindIdTv = findViewById(R.id.invlaidFreindIdTv);
        friendIdEt = findViewById(R.id.friendIdEt);

        //init
        addFriendLl.setVisibility(View.GONE);
        invalidfreindIdTv.setVisibility(View.GONE);

        //intent
        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("USERNAME");
        userObjId = (String)intent.getSerializableExtra("USER_OBJ_ID");

        //make request
        String getFriendListRequest  = RequestGenerator.getFriendListRequest(userObjId);
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
