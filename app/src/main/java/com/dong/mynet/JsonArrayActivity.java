package com.dong.mynet;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.dong.bean.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonArrayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_array);
        try {
            InputStream in = this.getAssets().open("test.json");
           /* byte[] buffer=new byte[is.available()];
            in.read(buffer);
            String content=new String(buffer);*/
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);

            }
            String content=sb.toString();
           /* Log.i("250", "**********kkk"+content);
            JSONArray jsonArray=new JSONArray(content);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                List<Person> list=new ArrayList<Person>();
                Person person=new Person();
                person.setName(jsonObject.getString("name"));
                person.setAge(jsonObject.getInt("age"));
                list.add(person);
                Log.i("250", "**********kkk" + list);
            }*/
            Gson gson=new Gson();
            List<Person> list=   gson.fromJson(content,new TypeToken<List<Person>>(){}.getType());
            for(int i=0;i<list.size();i++){
                Person p=list.get(i);
                Log.i("250", "**********kkk" + p.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
