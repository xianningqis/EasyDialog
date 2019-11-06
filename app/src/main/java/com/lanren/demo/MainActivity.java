package com.lanren.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lanren.easydialog.AnimatorHelper;
import com.lanren.easydialog.DialogViewHolder;
import com.lanren.easydialog.EasyDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.biao0).setOnClickListener(this);
        findViewById(R.id.biao1).setOnClickListener(this);
        findViewById(R.id.biao2).setOnClickListener(this);
        findViewById(R.id.biao3).setOnClickListener(this);
        findViewById(R.id.biao4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.biao0:
                new EasyDialog(this, R.layout.dialog_more_view) {
                    @Override
                    public void onBindViewHolder(DialogViewHolder holder) {

                    }
                }.backgroundLight(0.2)
                        .setCanceledOnTouchOutside(false)
                        .setCancelAble(true)
                        .setViewBottom(view)
                        .setCustomAnimations(AnimatorHelper.TOP_IN_ANIM, AnimatorHelper.TOP_OUT_ANIM)
                        .showDialog();

                break;
            case R.id.biao1:
                new EasyDialog(this, R.layout.dialog_more_view) {
                    @Override
                    public void onBindViewHolder(DialogViewHolder holder) {

                    }
                }.backgroundLight(0.2)
                        .setCanceledOnTouchOutside(true)
                        .setCancelAble(true)
                        .setViewRighBottom(view)
                        .showDialog(true);
                break;
            case R.id.biao2:
                new EasyDialog(this, R.layout.dialog_more_view) {
                    @Override
                    public void onBindViewHolder(DialogViewHolder holder) {

                    }
                }.backgroundLight(0.2)
                        .setCanceledOnTouchOutside(true)
                        .setCancelAble(true)
                        .setViewRighBottom(view)
                        .showDialog(true);
                break;

            case R.id.biao3:
                new EasyDialog(this, R.layout.dialog_more_view) {
                    @Override
                    public void onBindViewHolder(DialogViewHolder holder) {

                    }
                }.backgroundLight(0.2)
                        .setCanceledOnTouchOutside(true)
                        .setCancelAble(false)
                        .setViewBottom(view)
                        .setCustomAnimations(AnimatorHelper.TOP_IN_ANIM, AnimatorHelper.TOP_OUT_ANIM)
                        .showDialog();
                break;
            case R.id.biao4:
                new EasyDialog(this, R.layout.dialog_more_view) {
                    @Override
                    public void onBindViewHolder(DialogViewHolder holder) {

                    }
                }.backgroundLight(0.2)
                        .setCanceledOnTouchOutside(true)
                        .setCancelAble(true)
                        .setViewBottom(view)
                        .showDialog(true);
                break;
        }
    }
}
