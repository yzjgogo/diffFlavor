package com.yin.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


//import com.yin.myapp1.App1MainActivity;

import com.yin.myapp1.App2Activity;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
        //可以去 BuildConfig 中查看Gradle中定义的常量
//        Log.e("yin", BuildConfig.DOMAIN_NAME);
//        Log.e("yin","应用基本信息:"+getAppInfo());
        String signInfo = "";
        Map<String,String> signMap = Signature.getSignatureInfo(this);
        for (String key : signMap.keySet()) {
            if (key.equals("subjectDN")) {
                signInfo = "签名信息："+ key + "：" + signMap.get(key);
            }
        }
        tvContent.setText("应用基本信息:\n"+getAppInfo()+"\n"+signInfo);
        findViewById(R.id.bt_to_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,App2Activity.class);
                startActivity(intent);
            }
        });
    }
    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = this.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return "包名 ："+pkName + "\nversionName : " + versionName + "\nversionCode : " + versionCode
                    +"\nBUILD_TYPE : "+BuildConfig.BUILD_TYPE+"\nFLAVOR : "+BuildConfig.FLAVOR
                    +"\nFLAVOR_APP : "+BuildConfig.FLAVOR_APP+"\nFLAVOR_SERVER : "+BuildConfig.FLAVOR_SERVER
                    +"\nDOMAIN_NAME : "+BuildConfig.DOMAIN_NAME;
        } catch (Exception e) {
        }
        return null;
    }
}
