package com.sangyu.smsgroup;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.Vector;

public class ContactActivity extends Activity implements OnClickListener {
    private Vector<ContactInfo> mData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Context mContext = ContactActivity.this;
        ListView list_contact = findViewById(R.id.lv_contact);
        Button bt_choose_accepter = findViewById(R.id.bt_choose_accepter);
        bt_choose_accepter.setOnClickListener(this);
        mData = getContacts();
        ContactAdapter mAdapter = new ContactAdapter(mData, mContext);
        list_contact.setAdapter(mAdapter);
    }

    public Vector<ContactInfo> getContacts(){
        //①查询raw_contacts表获得联系人的id
        Vector<ContactInfo> contactInfos=new Vector<>();
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //查询联系人数据
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while(cursor.moveToNext())
        {
            //获取联系人姓名,手机号码
            String cID = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactInfos.add(new ContactInfo(false,cName,cNum));
        }
        cursor.close();
        return contactInfos;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_choose_accepter:
                StringBuilder Data = new StringBuilder();
                for (ContactInfo item:mData){
                    if(item.isSelected()){
                        Data.append(item.getDesplayName()).append(":").append(item.getPhoneNum()).append("#");
                    }
                }
                SharedPreferences sp = this.getSharedPreferences("info",MODE_WORLD_READABLE);
                //获取sp编辑器
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("data", Data.toString());
                edit.apply();
                //创建意图对象
                Intent intent = new Intent(ContactActivity.this,MainActivity.class);
                //激活意图
                startActivity(intent);
                break;
        }
    }
}
