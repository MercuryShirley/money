package com.example.money;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class rate extends AppCompatActivity {

    float dollar_rate=0.35f;
    float euro_rate=0.28f;
    float won_rate=501f;
    float dollar2,euro2,won2;
    EditText dollarText,euroText,wonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        Intent intent=getIntent();
        float dollar2=intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro2=intent.getFloatExtra("euro_rate_key",0.0f);
        float won2=intent.getFloatExtra("won_rate_key",0.0f);
        dollarText=findViewById(R.id.dollar_rate);
        euroText=findViewById(R.id.euro_rate);
        wonText=findViewById(R.id.won_rate);
        dollarText.setText(String.valueOf(dollar2));
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));
    }
    public void  openOne(View btn)
    {
        Intent config=new Intent(this,MainActivity.class);
        config.putExtra("dollar_rate1",dollar_rate);
        config.putExtra("euro_rate1",euro_rate);
        config.putExtra("won_rate1",won_rate);

        //将rate数据存入myrate文档里

        startActivityForResult(config,1);
        //关闭当前窗口
        finish();
    }



}