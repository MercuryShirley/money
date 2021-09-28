package com.example.money;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText rmb1;
    float dollar_rate=0.35f;
    float euro_rate=0.28f;
    float won_rate=501f;
    double rmb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void rmb(View add){
        rmb1= findViewById(R.id.inputrmb);
        rmb = Double.valueOf(rmb1.getText().toString());
        TextView out=findViewById(R.id.outputrmb);
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
    public void  openOne(View btn)
    {
        Intent config=new Intent(this,rate.class);
        config.putExtra("dollar_rate1",dollar_rate);
        config.putExtra("euro_rate1",euro_rate);
        config.putExtra("won_rate1",won_rate);
        startActivity(config);
    }
    public void config(){
        Intent intent=getIntent();
        float dollar_rate=intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro_rate=intent.getFloatExtra("euro_rate_key",0.0f);
        float won_rate=intent.getFloatExtra("won_rate_key",0.0f);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1&&requestCode==3)
        {
            dollar_rate=data.getFloatExtra("dollar_rate_key",0.1f);
            euro_rate=data.getFloatExtra("euro_rate_key",0.1f);
            won_rate=data.getFloatExtra("won_rate_key",0.1f);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.setting)
        {
            config();
        }
        return super.onOptionsItemSelected(item);
    }
}