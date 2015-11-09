package com.justin.goblinmusclestick;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Justin on 2015/10/24.
 */
public class GenerateQR extends AppCompatActivity {
    private Bitmap bm;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr);

        //Toast.makeText(this,json,Toast.LENGTH_SHORT).show();
        final ImageView img = (ImageView)findViewById(R.id.QrImg);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        ll.getBackground().setAlpha(100);

         handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1) {
                    img.setImageBitmap(bm);
                    Animation am = new ScaleAnimation(0.0f,1f,0.0f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    am.setDuration(500);
                    am.setRepeatCount(0);
                    img.setAnimation(am);
                    am.startNow();

                }
            }
        };
        final Runnable rb = new Runnable() {
            @Override
            public void run() {
                Log.d("g","thread");
                EditText ed_username = (EditText)findViewById(R.id.username);
                EditText ed_bank = (EditText)findViewById(R.id.bankcode);
                EditText ed_amount = (EditText)findViewById(R.id.amount);
                String json = "{\"username\":\""+ed_username.getText().toString()+"\",\"bank_code\":\""+ed_bank.getText().toString()+"\",\"amount\":\""+ed_amount.getText().toString()+"\"}";
                String imgurl = "http://api.qrserver.com/v1/create-qr-code/?data="+json;
                try {
                    URL url = new URL(imgurl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    InputStream is = conn.getInputStream();
                    bm = BitmapFactory.decodeStream(is);

                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MultiThread","suck");
                new Thread(rb).start();
                //img.setClickable(false);
            }
        });


    }
}
