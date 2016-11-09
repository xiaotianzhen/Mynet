package com.dong.mynet;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import it.sephiroth.android.library.picasso.Picasso;

public class httpUrlConnection extends AppCompatActivity {
    private ImageView image;
    private ProgressDialog progressDialog = null;
    private TextView tv_show;
    String city;
    String temp;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tv_show.setText("你所在的城市为：" + city + "温度为：" + temp);
                Picasso.with(httpUrlConnection.this).load("http://img1.gtimg.com/news/pics/hv1/37/195/1468/95506462.jpg").into(image);
            } else {
                Toast.makeText(httpUrlConnection.this, "解析失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlhttpcilent);
        image = (ImageView) this.findViewById(R.id.image);
        tv_show = (TextView) findViewById(R.id.tv_show);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // String path = "http://img1.gtimg.com/news/pics/hv1/37/195/1468/95506462.jpg";

                try {
                    String path = "http://www.weather.com.cn/adat/sk/101010100.html";
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(1000);
                    con.setReadTimeout(1000);
                    con.setDoInput(true);
                    int code = con.getResponseCode();
                    Log.i("520it", "*************" + code);

                       /* InputStream in = con.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);*/
                    if (code == 200) {
                        InputStream in = con.getInputStream();
                        InputStreamReader inr = new InputStreamReader(in);
                        BufferedReader bufferedReader = new BufferedReader(inr);
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line);

                        }

                        String content = sb.toString();
                        Log.i("520it", "*************" + content);
                        JSONObject jsonObject = new JSONObject(content);
                        JSONObject json = jsonObject.getJSONObject("weatherinfo");
                        city = json.getString("city");
                        temp = json.getString("temp");
   /* handler.post(new Runnable() {
        @Override
        public void run() {
            tv_show.setText("你所在的城市为：" + city + "温度为：" + temp);
        }
    });*/
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = city;
                        msg.obj = temp;
                        handler.sendMessage(msg);


                    } else {
                        Message msg = Message.obtain();
                        msg.what = 2;
                        handler.sendMessage(msg);

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();

    }
}
