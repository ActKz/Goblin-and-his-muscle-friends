package com.justin.goblinmusclestick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Justin on 2015/10/25.
 */
public class ScanQR extends AppCompatActivity implements OnQRCodeReadListener {
    private TextView myTextView;
    private QRCodeReaderView mydecoderview;
    private ImageView line_image;
    private String tmp;
    private boolean dialogShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

        myTextView = (TextView) findViewById(R.id.exampleTextView);

        line_image = (ImageView) findViewById(R.id.red_line_image);

        /*TranslateAnimation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.5f);
        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        line_image.setAnimation(mAnimation);*/
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String recv = (String)msg.obj;
            //Toast.makeText(ScanQR.this,recv,Toast.LENGTH_LONG).show();

            AlertDialog.Builder dialog = new AlertDialog.Builder(ScanQR.this);
            dialog.setTitle("交易完成!!");
            dialog.setIcon(android.R.drawable.ic_dialog_info);
            String list = "";
            try {
                list+="姓名: "+new JSONObject(recv).getString("account_name");
                list+="\n帳戶: "+new JSONObject(recv).getString("account_id");
                list+="\n餘額: "+new JSONObject(recv).getString("balance");
                list+="\n匯款對象: "+new JSONObject(recv).getString("payee_account_name");
                list+="\n匯款帳號: "+new JSONObject(recv).getString("payee_account_id");
                list+="\n交易金額: "+new JSONObject(recv).getString("transaction_amount");
                list+="\nATM編號: "+new JSONObject(recv).getString("atm_no");
                list+="\n交易銀行代碼: "+new JSONObject(recv).getString("payee_bank_code");
                list+="\n交易日期: "+new JSONObject(recv).getString("transaction_date");
                list+="\n交易時間: "+new JSONObject(recv).getString("transaction_time");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            dialog.setMessage(list);

            dialog.setCancelable(false);
            dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //ScanQR.this.finish();
                }
            });

            dialog.show();
        }
    };

    Thread r1 = new Thread(new Runnable() {
        @Override
        public void run() {
            Log.d("Scan", "run?");
            try {
                String postUrl = "http://actkz.nctucs.net/transfer.php";
                Log.d("URL",postUrl);
                URL url = new URL(postUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("token","h6mCxToLEBhtA7LVd4uM4gTcZG8F6UJs47g6kWJ")
                        .appendQueryParameter("target_account",new JSONObject(tmp).getString("username"))
                        .appendQueryParameter("username", "tom")
                        .appendQueryParameter("amount",new JSONObject(tmp).getString("amount"))
                        .appendQueryParameter("bank_code",new JSONObject(tmp).getString("bank_code"));


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
                }
                else {
                    response="";
                }
                Log.d("inputStream",response);


                Message msg = Message.obtain();
                msg.obj = response;
                msg.setTarget(handler);
                msg.sendToTarget();

                //Toast.makeText(ScanQR.this,is.toString(),Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        /*Intent newAct = new Intent();
        newAct.setClass(this,Next.class);
        newAct.putExtra("message",text);
        startActivity(newAct);*/
        //Toast.makeText(ScanQR.this, text, Toast.LENGTH_SHORT).show();
        tmp = text;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Final Confirm");
        try {
            dialog.setMessage("以下的個人相關資訊正確嗎?\nUsername: "+new JSONObject(text).getString("username")+"\nBank Code: "+new JSONObject(text).getString("bank_code")+"\nAmount: " + new JSONObject(text).getString("amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                r1.start();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ScanQR.this.finish();
            }
        });

        if(!dialogShowing){
            dialog.show();
            dialogShowing = true;
        }

    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }
}


