package com.gxy.animationdemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;
    private Handler mHandler = new Handler();
    private List<ImageView> list = new ArrayList<>();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        dialog = new Dialog(context, R.style.my_dialog_style);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
        final ImageView img01 = (ImageView) view.findViewById(R.id.img_01);
        final ImageView img02 = (ImageView) view.findViewById(R.id.img_02);
        final ImageView img03 = (ImageView) view.findViewById(R.id.img_03);
        // 这几个show和close的操作千万不要用集合循环去操作，否则在显示dialog时会出现dialog闪一下就消失的情况
        showAnim(img01, 150);
        showAnim(img02, 200);
        showAnim(img03, 250);
        Button button = (Button) view.findViewById(R.id.btn_dialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(img03, 100, 380);
                closeAnim(img02, 150, 430);
                closeAnim(img01, 200, 480);
            }
        });

    }

    private void showAnim(final ImageView i, int d) {
        i.setVisibility(View.INVISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                i.setVisibility(View.VISIBLE);
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(i, "translationY", 1000, 0);
                fadeAnim.setDuration(600);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(600);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        i.clearAnimation();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {}
                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
            }
        },  d);
    }

    private void closeAnim(final ImageView img, int i, int j) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(img, "translationY", 0, 1000);
                fadeAnim.setDuration(600);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(600);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        img.setVisibility(View.INVISIBLE);
                        img.clearAnimation();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        }, i);
        if(img.getId() == R.id.img_01){
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, j);
        }
    }
}
