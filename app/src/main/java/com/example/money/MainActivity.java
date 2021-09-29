package com.example.money;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.Reader;

public class MainActivity extends AppCompatActivity implements Runnable {

    EditText rmb1;
    float dollar_rate = 0.35f;
    float euro_rate = 0.28f;
    float won_rate = 501f;
    double rmb;
    TextView result;
    private static final String TAG = "rmb";
    Handler handler;//不要写任何方法，只能定义

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.outputrmb);

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 6) {
                    String str = (String) msg.obj;
                    result.setText(str);
                }
                super.handleMessage(msg);
            }
        };
        //启动线程
        Thread thread = new Thread(this);
        thread.start();//this.run

        //将myrate文档里面的rate数据取出
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollar_rate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euro_rate = sharedPreferences.getFloat("euro_rate", 0.0f);
        won_rate = sharedPreferences.getFloat("won_rate", 0.0f);

    }

    public void rmb(@NonNull View add) {
        rmb1 = findViewById(R.id.inputrmb);
        rmb = Double.valueOf(rmb1.getText().toString());
        TextView out = findViewById(R.id.outputrmb);
        /*if(rmb1.length()>0) {*/
        if (add.getId() == R.id.dollar) {
            rmb += rmb * dollar_rate;
        } else if (add.getId() == R.id.euro) {
            rmb += rmb * euro_rate;
        } else {
            rmb += rmb * won_rate;
        }
        out.setText(String.valueOf(rmb));
    }

    /*else
    {
        Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
    }
}*/
    public void openOne(View btn) {
        Intent config = new Intent(this, rate.class);
        config.putExtra("dollar_rate1", dollar_rate);
        config.putExtra("euro_rate1", euro_rate);
        config.putExtra("won_rate1", won_rate);
        startActivityForResult(config, 1);
    }

    public void config() {
        Intent intent = getIntent();
        float dollar_rate = intent.getFloatExtra("dollar_rate_key", 0.0f);
        float euro_rate = intent.getFloatExtra("euro_rate_key", 0.0f);
        float won_rate = intent.getFloatExtra("won_rate_key", 0.0f);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            dollar_rate = data.getFloatExtra("dollar_rate_key", 0.1f);
            euro_rate = data.getFloatExtra("euro_rate_key", 0.1f);
            won_rate = data.getFloatExtra("won_rate_key", 0.1f);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            //菜单事件代码
            config();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        Log.i(TAG, "run...");
        //延迟
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发送消息
        Message msg = handler.obtainMessage(6);
        //message=6
        msg.obj = "Hello from run";
        handler.sendMessage(msg);

        //获取网络数据
        URL url = null;
        try {
            url = new URL("https://www.360.cn/");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream2String(in);
            Log.i(TAG, "run: html=" + html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "utf-8");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}
