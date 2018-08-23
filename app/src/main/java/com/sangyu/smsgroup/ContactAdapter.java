package com.sangyu.smsgroup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private List<ContactInfo> mContacts;
    private Context mContext;
    HashMap<Integer,View> map = new HashMap<Integer,View>();

    public ContactAdapter(List<ContactInfo> mContacts,Context mContext) {
        this.mContext = mContext;
        this.mContacts = new ArrayList<>();
        this.mContacts = mContacts;
        for (int i = 0; i < mContacts.size(); i++) {
            mContacts.get(i).setSelected(false);
        }
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder =null;
        if(map.get(position) == null){
            Log.e("ContactActivity","position1 = "+position);
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.contact_list, null);
            holder = new ViewHolder();
            holder.selected = (CheckBox)view.findViewById(R.id.cb_itemcheck_send);
            holder.name = (TextView)view.findViewById(R.id.tv_name);
            holder.phone = (TextView)view.findViewById(R.id.tv_phone);
            final int p = position;
            map.put(position, view);
            holder.selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    CheckBox cb = (CheckBox) compoundButton;
                    cb.setChecked(b);
                    mContacts.get(p).setSelected(b);
                }
            });
            view.setTag(holder);
        }else{
            Log.e("ContactActivity","position2 = "+position);
            view = map.get(position);
            holder = (ViewHolder)view.getTag();
        }
        holder.selected.setChecked(mContacts.get(position).isSelected());
        holder.name.setText(mContacts.get(position).getDesplayName());
        holder.phone.setText(mContacts.get(position).getPhoneNum());

        return view;
    }

    static class ViewHolder{
        CheckBox selected;
        TextView name;
        TextView phone;
    }

}
