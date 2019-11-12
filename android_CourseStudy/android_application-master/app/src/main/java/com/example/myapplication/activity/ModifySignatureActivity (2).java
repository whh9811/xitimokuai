package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.w3c.dom.Text;

public class ModifySignatureActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editSignature;
    private ImageView clearImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_signature);
        initView();
        initToolBar();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            String oldSign = bundle.getString("signature");
            editSignature.setText(oldSign);
        } else {
            editSignature.setText("");
        }
//        initData();
        clearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSignature.setText("");
            }
        });
    }

//    private String title;
//    private String value;
//    private int flag;
//    private void initData(){
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null){
//            title = bundle.getString("title");
//            value = bundle.getString("value");
//            flag = bundle.getInt("flag");
//        }
//    }
    //加载选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info,menu);
        return true;
    }

    //菜单项的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                save();
                break;
            case R.id.item_cancel:
                ModifySignatureActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        String signature = editSignature.getText().toString();
        if (TextUtils.isEmpty(signature)){
            Toast.makeText(ModifySignatureActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(ModifySignatureActivity.this, UserInfoActivity.class);
            intent.putExtra("signature", signature);
            setResult(RESULT_OK, intent);
            Toast.makeText(ModifySignatureActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
            ModifySignatureActivity.this.finish();
        }
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.title_bar);
        toolbar.setTitle("设置签名");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifySignatureActivity.this.finish();
            }
        });
    }

    private void initView() {
        editSignature = findViewById(R.id.edit_signature);
        clearImage = findViewById(R.id.clear_signature);
    }
}
