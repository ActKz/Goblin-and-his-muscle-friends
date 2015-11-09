package com.justin.goblinmusclestick;

/**
 * Created by Eric on 2015/10/25.
 */
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Login extends Activity{

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final EditText CodeEditText = (EditText)findViewById(R.id.EditText02);
        final CheckBox CodeCheckBox = (CheckBox)findViewById(R.id.CheckBox01);
        final TextView tv1 = (TextView)findViewById(R.id.TextViewCheckBox);
        tv1.setText("顯示密碼");
        login = (Button)findViewById(R.id.ButtonLogin);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        ll.getBackground().setAlpha(100);
        CodeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (CodeCheckBox.isChecked()) {
                    CodeEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//顯示密碼
                } else {
                    CodeEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());  //隱藏密碼
                }
            }
        });
        login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new Thread(sendable).start();
            }
        });
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String recv = (String)msg.obj;
            Toast.makeText(Login.this,recv,Toast.LENGTH_LONG).show();
        }
    };

    Runnable sendable= new Runnable() {
        @Override
        public void run() {
            Log.d("Scan", "run?");
            try {
                String postUrl = "http://actkz.nctucs.net/login.php";
                Log.d("URL", postUrl);
                URL url = new URL(postUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                final EditText CodeEditText = (EditText)findViewById(R.id.EditText02);
                String pwd = CodeEditText.getText().toString();

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", "alice")
                        .appendQueryParameter("password", pwd);


                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

                int responseCode=conn.getResponseCode();
                String response="";
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                    response="password error!";
                    Message msg = Message.obtain();
                    msg.obj = response;
                    msg.setTarget(handler);
                    msg.sendToTarget();
                }
                Log.d("inputStream",response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}