package com.example.auser.t103001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);

    }
    public void clickGet(View v)
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("http://rate.bot.com.tw/xrt?Lang=zh-TW");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    String str;
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    while((str = br.readLine()) != null)
                    {
                        sb.append(str);
                    }
                    br.close();
                    isr.close();
                    is.close();
                    String result = sb.toString();
                    int loc1 = result.indexOf("0.2719");
                    // System.out.println(loc1);
                    int loc2 = result.indexOf("日圓 (JPY)");
                    // System.out.println(loc2);
                    int loc3 = result.indexOf("本行現金賣出", loc2);
                    // System.out.println("loc1:" + loc1 + "," + "loc3:" + loc3);


                    final String value = result.substring(loc3 + 56, loc3 + 62);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(value);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void clickTest(View v)
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText("Okay!!!");
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}

