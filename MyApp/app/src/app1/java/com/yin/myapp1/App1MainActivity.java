package com.yin.myapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yin.myapp.R;

/**
 * 根文件夹app1不能随便命名，要和你在gradle总配置的app1一样，否则不识别，例如我那里有个app7是无法在app7新建activity的，你一新建就跑到了main中
 * 当你在Build Variants中的Build Variant选择了app1则app2的所有代码处于无效状态，就跟没有一样
 * app1和app2可以引用main总的类、方法等，但是当为app1时无法引用app2中的内容(此时app2的代码是死的)，反之也一样
 */
public class App1MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app1_main);
    }
}
