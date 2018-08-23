package com.sangyu.smsgroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{
    private EditText et_input_numbers;
    private EditText et_input_messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_input_numbers = findViewById(R.id.et_input_numbers);
        et_input_messages = findViewById(R.id.et_input_messages);
        Button bt_open_address_book = findViewById(R.id.bt_open_address_book);
        Button bt_send = findViewById(R.id.bt_send);

        bt_open_address_book.setOnClickListener(this);
        bt_send.setOnClickListener(this);

        SharedPreferences sp = this.getSharedPreferences("info", MODE_WORLD_READABLE);
        //设置数据
        et_input_numbers.setText(sp.getString("data",""));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_open_address_book:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ContactActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_send:
                sendMessage(this);
                break;
        }
    }



    private void sendMessage(Context context) {
        String numbers = et_input_numbers.getText().toString().trim();
        String messages = et_input_messages.getText().toString().trim();
        System.out.println(numbers);
        String [] numberArray = numbers.split("#");
        if (TextUtils.isEmpty(numbers)||TextUtils.isEmpty(messages)){
            toastText(context,"号码或短信内容不能为空");
        }
        else{
            for (String number : numberArray){
                String [] name_number = number.split(":");
                SmsManager smsmanager = SmsManager.getDefault();
                ArrayList<String> charsArray = smsmanager.divideMessage(name_number[0]+","+messages);
                for (String sms : charsArray){
                    smsmanager.sendTextMessage(name_number[1],null,sms,null,null);
                }
                toastText(context,"发给"+number+"的短信已发送成功");
            }
        }
    }

    private void toastText(Context context, String text) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
