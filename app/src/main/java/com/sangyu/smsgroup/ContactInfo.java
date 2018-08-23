package com.sangyu.smsgroup;

public class ContactInfo {
    private boolean selected;   //是否选中
    private String desplayName;//姓名
    private String phoneNum; // 电话号码

    public ContactInfo() {
    }

    public ContactInfo(boolean selected, String desplayName, String phoneNum) {
        this.selected = selected;
        this.desplayName = desplayName;
        this.phoneNum = phoneNum;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDesplayName() {
        return desplayName;
    }

    public void setDesplayName(String desplayName) {
        this.desplayName = desplayName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
