package com.dong.mynet;


import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;


public class OkHttpActvity extends AppCompatActivity {
    private ImageView im;
    private RelativeLayout oklayount;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
               /* Bitmap bitmap = (Bitmap) msg.obj;
                im.setImageBitmap(bitmap);*/
                String city = (String) msg.obj;
                String temp = (String) msg.obj;
                oklayount= (RelativeLayout) findViewById(R.id.oklayout);
                TextView textView = new TextView(OkHttpActvity.this);
                textView.setText("你所在的城市为：" + city + ",温度为：" + temp);
                oklayount.addView(textView);
            }
            if (msg.what == 2) {
                Toast.makeText(OkHttpActvity.this, "连接错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_actvity);
        im = (ImageView) findViewById(R.id.im);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    /*final OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder().method("GET", null).url("http://img1.gtimg.com/news/pics/hv1/37/195/1468/95506462.jpg").build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Toast.makeText(OkHttpActvity.this, "图片加载失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {

                            Message msg = Message.obtain();
                            msg.what = 1;
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                }
            });*/

                    OkHttpClient okHttpClient = new OkHttpClient();
                  /*  RequestBody formBody = new FormBody.Builder().add("", "").build();*/
                    Request requst = new Request.Builder().url("http://www.weather.com.cn/adat/sk/101010100.html").
                            method("GET", null).build();
                    Call call = okHttpClient.newCall(requst);
                    call.enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, okhttp3.Response response) throws IOException {
                            if (response.isSuccessful()) {
                                InputStream in = response.body().byteStream();
                                Log.i("520it", "***************" + response.body().toString());
                                InputStreamReader reader = new InputStreamReader(in);
                                BufferedReader bufferedReader = new BufferedReader(reader);
                                String line;
                                StringBuilder sb = new StringBuilder();
                                if ((line = bufferedReader.readLine()) != null) {
                                    sb.append(line);
                                }

                                try {
                                    String content = sb.toString();
                                    Log.i("520it", "***************" + content);
                                    JSONObject jsonObject = new JSONObject(content);
                                    JSONObject json = jsonObject.getJSONObject("weatherinfo");
                                    final String city = json.getString("city");
                                    final String temp = json.getString("temp");
                                   /* handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            oklayount= (RelativeLayout) findViewById(R.id.oklayout);
                                            TextView textView = new TextView(OkHttpActvity.this);
                                            textView.setText("你所在的城市为：" + city + ",温度为：" + temp);
                                            oklayount.addView(textView);

                                        }
                                    });*/
                                    Message msg = Message.obtain();
                                    msg.what = 1;
                                    msg.obj = city;
                                    msg.obj=temp;
                                    handler.sendMessage(msg);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Message msg = Message.obtain();
                                msg.what = 2;

                                handler.sendMessage(msg);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
