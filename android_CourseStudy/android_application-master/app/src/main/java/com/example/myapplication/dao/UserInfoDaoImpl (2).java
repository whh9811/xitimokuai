package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.entity.User;
import com.example.myapplication.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDaoImpl implements UserInfoDao {
    private DBHelper helper;//用于创建数据库，获取数据对象
    private SQLiteDatabase db;//用于执行sql语句

    public UserInfoDaoImpl(Context context){
        helper = DBHelper.getInstance(context);//创建数据库
    }

    @Override
    public List<User> select() {
        List<User> users = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TBL_NAME_USER,null,null,null,
                null,null,null);
        if (cursor!=null && cursor.moveToFirst()){
            do {
                User userList = new User();
                userList.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
                userList.setNickname(cursor.getString(cursor.getColumnIndex("nick_name")));
                userList.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                userList.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
                users.add(userList);
                cursor.close();
            }while (cursor.moveToNext());
        }
        db.close();
        return users;
    }

    @Override
    public User select(String username) {
        User user = null;
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TBL_NAME_USER,null,"user_name=?",
                new String[]{username},null,null,null);
        if (cursor !=null && cursor.moveToFirst()){
            user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
            user.setNickname(cursor.getString(cursor.getColumnIndex("nick_name")));
            user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            user.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
            cursor.close();
        }
        db.close();
        return user;
    }

    @Override
    public void insert(User user) {
        ContentValues values = new ContentValues();
        values.put("user_name",user.getUsername());
        values.put("nick_name",user.getNickname());
        values.put("sex",user.getSex());
        values.put("signature",user.getSignature());

        db =helper.getWritableDatabase();
        db.insert(DBHelper.TBL_NAME_USER,null,values);

//        String sql = "insert into " + DBHelper.TBL_NAME_USER+" values(null,?,?,?,?)";
//        db.execSQL(sql,new String[]{
//                user.getUsername(),
//                user.getNickname(),
//                user.getSex(),
//                user.getSignature()
//        });

        db.close();
    }

    @Override
    public void update(User user) {
        ContentValues values = new ContentValues();
        values.put("user_name",user.getUsername());
        values.put("nick_name",user.getNickname());
        values.put("sex",user.getSex());
        values.put("signature",user.getSignature());

        db =helper.getWritableDatabase();
        db.update(DBHelper.TBL_NAME_USER,values,"user_name = ?",new String[]{user.getUsername()});
        db.close();
    }

    @Override
    public void delete(User user) {
        db =helper.getWritableDatabase();
        db.delete(DBHelper.TBL_NAME_USER,"user_name = ?",new String[]{user.getUsername()});
        db.close();
    }
}
