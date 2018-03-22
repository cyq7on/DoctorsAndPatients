package com.cyq7on.dap.bean;

import com.cyq7on.dap.db.NewFriend;
import cn.bmob.v3.BmobUser;

/**
 * @author :smile
 * @project:User
 * @date :2016-01-22-18:11
 */
public class User extends BmobUser {

    private String avatar;
    private String age;
    private int sex;
    //科室
    private String department;
    //病历
    private String record;

    public User(){}

    public User(NewFriend friend){
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(friend.getAvatar());
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
