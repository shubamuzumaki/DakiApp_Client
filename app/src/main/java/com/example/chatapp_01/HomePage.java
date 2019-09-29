package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    TextView noFriendPopUpTv;
    LinearLayout addFriendLl;
    TextView invalidFriendIdTv;
    EditText friendIdEt;
    ListView contactsLv;

    ArrayAdapter<String> contactListAdapter;
    ArrayList<String> contactList;

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
                    noFriendPopUpTv.setText("");
                    contactList = new ArrayList<>(Arrays.asList(friendList));
                    contactListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, contactList);
                    contactsLv.setAdapter(contactListAdapter);
                }
                else
                {
                    noFriendPopUpTv.setText("No Friends!!!\n Click AddFriend to Connect to Your Friends Today");
                }
                break;
//            -------------------------------------------------------------
            case CommunicationFlags.ADD_FRIEND_RESPONSE_SUCCESSFULL:
                System.out.println("Friend Added Successfully");
                contactList.add(ResponseProcessor.getFriendId(response));
                contactListAdapter.notifyDataSetChanged();
                Toast.makeText(this,"Friend Added Successfully",Toast.LENGTH_SHORT).show();
                break;
//            -------------------------------------------------------------
            case CommunicationFlags.ADD_FRIEND_RESPONSE_FAILED:
                invalidFriendIdTv.setVisibility(View.VISIBLE);
                invalidFriendIdTv.setText("Please Enter a Valid Friend's Id");
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
        invalidFriendIdTv.setVisibility(View.GONE);

    }

    public void onClickSubmitFriendId(View view)
    {
        invalidFriendIdTv.setVisibility(View.GONE);
        String friendId = friendIdEt.getText().toString();
        if(friendId.isEmpty())
        {
            invalidFriendIdTv.setVisibility(View.VISIBLE);
            invalidFriendIdTv.setText("Id can't be empty.Choose a Valid Id");
            return;
        }

        String addFriendRequest = RequestGenerator.getAddFriendRequest(userObjId,friendId);
        new NetTask(this).execute(addFriendRequest);
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

        //initViews
        initViews();

        //init
        addFriendLl.setVisibility(View.GONE);
        invalidFriendIdTv.setVisibility(View.GONE);

        //intent
        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("USERNAME");
        userObjId = (String)intent.getSerializableExtra("USER_OBJ_ID");

        //make request
        String getFriendListRequest  = RequestGenerator.getFriendListRequest(userObjId);
        new NetTask(this).execute(getFriendListRequest);

        //set Listener for ListView
        contactsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent chatActivity = new Intent(getApplicationContext(),ChatActivity.class);
                chatActivity.putExtra("USERNAME",username);
                chatActivity.putExtra("USER_OBJ_ID",userObjId);
                chatActivity.putExtra("FRIEND_ID",contactList.get(position));
                startActivity(chatActivity);

            }
        });
//        testFunction();
    }

    private void initViews()
    {
        noFriendPopUpTv = findViewById(R.id.noFreindPopUpTv);
        addFriendLl = findViewById(R.id.addFriendLl);
        invalidFriendIdTv = findViewById(R.id.invlaidFreindIdTv);
        friendIdEt = findViewById(R.id.friendIdEt);
        contactsLv = findViewById(R.id.contactLv);
    }

    void testFunction()
    {
        ArrayList<String> contacts = new ArrayList<>();
        contacts.add("Shubam");
        contacts.add("mall");

        contactListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,contacts);
        contactsLv.setAdapter(contactListAdapter);
    }
}
