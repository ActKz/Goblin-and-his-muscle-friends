package com.justin.goblinmusclestick;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainlayout;
    private int width;
    private int height;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display dis = getWindowManager().getDefaultDisplay();
        width = dis.getWidth();
        height = dis.getHeight();
        img = (ImageView) findViewById(R.id.img);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);


        //img.setImageResource(android.R.color.transparent);



        mainlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                float k = height*width-x*height-y*width;
                int mode = 0;
                if(k<0){
                    img.setImageResource(R.drawable.money);
                    mode = 1;
                } else if(k>0){
                    img.setImageResource(R.drawable.street);
                    mode = 2;
                } else{
                    return false;
                }
                ImageAnime(mode);

                return false;
            }
        });
    }
    public void ImageAnime(final int mode){

        Animation am = new AlphaAnimation(1f, 0f);
        Animation am2 = new ScaleAnimation(1.1f, 1.4f, 1.1f, 1.4f,width*0.5f,height*0.5f);
        am.setDuration(500);
        am.setRepeatCount(0);
        am2.setDuration(500);
        am2.setRepeatCount(0);

        AnimationSet ams = new AnimationSet(false);
        ams.addAnimation(am);
        ams.addAnimation(am2);

        img.setAnimation(ams);
        ams.startNow();

        ams.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img.setImageResource(android.R.color.transparent);
                if(mode == 1){
                    Log.d("Mode","1");
                    Intent intent = new Intent(MainActivity.this,ScanQR.class);
                    startActivity(intent);
                } else if(mode ==2){
                    Log.d("Mode","2");
                    Intent intent = new Intent(MainActivity.this,GenerateQR.class);
                    startActivity(intent);

                } else{
                    Log.e("Mode","?");
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}


