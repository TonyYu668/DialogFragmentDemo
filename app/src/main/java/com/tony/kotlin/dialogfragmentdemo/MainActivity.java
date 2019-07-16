package com.tony.kotlin.dialogfragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.tony.kotlin.widgt.dialog.YTDialog;
import com.tony.kotlin.widgt.dialog.YTDialogType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tony
 */
public class MainActivity extends AppCompatActivity implements YTDialog.ClickCallBack {
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i <= 39; i++) {
            list.add("item==" + i);
        }
    }

    public void prompt(View view) {
        YTDialog dialog = new YTDialog.Builder()
                .setType(YTDialogType.PROMPT)
                .setDataList(list)
                .setContent("书记15日赴内蒙古自治区考察调研。他首先来到赤峰市松山区兴安街道临潢家园社区，")
                .setTitle("标题")
                .setPositiveButtonText("Ok")
                .setNegativeButtonText("Cancel")
                .setHintText("Hint").show(MainActivity.this, MainActivity.class.toString());
    }

    public void input(View view) {
        YTDialog dialog = new YTDialog.Builder()
                .setType(YTDialogType.INPUT)
                .setDataList(list)
                .setContent("书记15日赴内蒙古自治区考察调研。他首先来到赤峰市松山区兴安街道临潢家园社区，")
                .setTitle("标题")
                .setClickCallBack(this)
                .setPositiveButtonText("Ok")
                .setClickCallBack(new YTDialog.ClickCallBack() {
                    @Override
                    public void onPositiveCallBack(String string) {
                        Toast.makeText(MainActivity.this, "input" + string, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNegativeCallBack() {

                    }
                })
                .setNegativeButtonText("Cancel")
                .setHintText("Hint")
                .show(MainActivity.this, MainActivity.class.toString());
    }

    public void list(View view) {

        YTDialog dialog = new YTDialog.Builder()
                .setType(YTDialogType.LIST)
                .setDataList(list)
                .setContent("书记15日赴内蒙古自治区考察调研。他首先来到赤峰市松山区兴安街道临潢家园社区，")
                .setTitle("标题")
                .setListClickCallBack(new YTDialog.ListClickCallBack() {
                    @Override
                    public void onItemCallBack(String string) {
                        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButtonText("Ok")
                .setNegativeButtonText("Cancel")
                .setHintText("Hint")
                .show(this, this.getClass().toString());
    }

    public void horizontal(View view) {
        YTDialog dialog = new YTDialog.Builder()
                .setType(YTDialogType.HORIZONTAL_BUTTON)
                .setDataList(list).setContent("书记15日赴内蒙古自治区考察调研。他首先来到赤峰市松山区兴安街道临潢家园社区，")
                .setTitle("标题")
                .setPositiveButtonText("Ok")
                .setNegativeButtonText("Cancel")
                .setHintText("Hint")
                .show(MainActivity.this, MainActivity.class.toString());
    }

    public void vertical(View view) {
        YTDialog dialog = new YTDialog.Builder()
                .setType(YTDialogType.VERTICAL_BUTTON)
                .setDataList(list).setContent("书记15日赴内蒙古自治区考察调研。他首先来到赤峰市松山区兴安街道临潢家园社区，了解社区基层党建、民族团结融合等情况。随后，他来到赤峰博物馆，了解当地历史文化沿革，同古典民族史诗《格萨（斯）尔》非物质文化遗产传承人亲切交谈。")
                .setTitle("标题")
                .setPositiveButtonText("Ok")
                .setNegativeButtonText("Cancel")
                .setHintText("Hint")
                .show(MainActivity.this, MainActivity.class.toString());
    }

    @Override
    public void onPositiveCallBack(@org.jetbrains.annotations.Nullable String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeCallBack() {

    }
}
