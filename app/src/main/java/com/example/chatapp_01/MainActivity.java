package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Networkable
{
    EditText usernameEditText,passwordEditText;
    TextView usernameHint,passwordHint;
    String username;

    @Override
    public void networkResponse(String response)
    {
        int flag = ResponseProcessor.getResponseHeader(response);
        switch(flag)
        {
            case CommunicationFlags.SIGN_UP_SUCCESSFULL:
                Toast.makeText(this,"SignUP Successfull",Toast.LENGTH_SHORT).show();
                System.out.println("Signup SuccesFull");
                break;
            case CommunicationFlags.SIGN_UP_FAILED:
                Toast.makeText(this,"SignUP Failed",Toast.LENGTH_SHORT).show();
                usernameHint.setText("Invalid username!");
                System.out.println("SignUp Failed");
                break;
            case CommunicationFlags.LOGIN_SUCCESSFULL:
                Toast.makeText(this,"Login Successfull",Toast.LENGTH_SHORT).show();
                System.out.println("Login Successfull");
                Intent intent = new Intent(this,HomePage.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("USER_OBJ_ID",ResponseProcessor.getUserObjectId(response));
                startActivity(intent);
                break;
            case CommunicationFlags.LOGIN_FAILED:
                usernameHint.setText("Invalid username or password");
                passwordHint.setText("Invalid username or password");
                Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show();
                System.out.println("Login Failed");
                break;
            case CommunicationFlags.CONNECTION_FAILED:
                Toast.makeText(this,"Server Down...\nPlease Try After Some Time",Toast.LENGTH_LONG).show();
                System.out.println("Server Down...");
                break;
            default:
                System.out.println("Invalid Response");
                break;
        }
    }

    public void onClickAuthenticate(View view)
    {
        //@TODO create password manager to validate password by regex
        Button button = (Button)view;
        username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        usernameHint.setText("");
        passwordHint.setText("");

        if(username.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Please Provide a Valid Username And Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(username.contains(" "))
        {
            usernameHint.setText("Please no Spaces in username.");
            return;
        }

        String netRequest = "";
        if(button.getId() == R.id.loginButton)
        {
            netRequest += CommunicationFlags.LOGIN + CommunicationFlags.SEPARATOR_1;
            Toast.makeText(this,"Logging you in...",Toast.LENGTH_SHORT).show();
        }
        else if(button.getId() == R.id.signupButton)
        {
            netRequest += CommunicationFlags.SIGN_UP + CommunicationFlags.SEPARATOR_1;
            Toast.makeText(this,"Signing Up...",Toast.LENGTH_SHORT).show();
        }

        netRequest += username + CommunicationFlags.SEPARATOR_2 + password;
        new NetTask(this).execute(netRequest);
    }//onClickAuthenticate

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cache variables
        usernameEditText = findViewById(R.id.usernameTextViewHomePage);
        passwordEditText = findViewById(R.id.passwordEditText);
        usernameHint = findViewById(R.id.usernameHintTextView);
        passwordHint = findViewById(R.id.passwordHintTextView);
    }
 }
