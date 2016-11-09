package com.dong.mynet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import com.dong.bean.Student;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmParserActivity extends AppCompatActivity {
    private Student student = null;
    private List<Student> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xm_parser);
        try {
            InputStream in = this.getAssets().open("test.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(in, "UTF-8");
            int evenType = parser.getEventType();
            int i=0;
            while (evenType != XmlPullParser.END_DOCUMENT) {
//开始标记和结束标记不在一起的eventype会各执行两次，开始标记和激素标记在一次的会执行两次
                i+=1;
                Log.i("520xml", "**************" +i);
                switch (evenType) {

                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        if ("student".equals(parser.getName())) {
                            student = new Student();
                            student.setId(parser.getAttributeValue(0));
                        }
                        if (student != null) {
                         Log.i("520xml", "**************" +parser.getName());
                            if ("name".equals(parser.getName())) {
                                student.setName(parser.nextText());
                            }
                            if ("age".equals(parser.getName())) {
                                student.setAge(Integer.valueOf(parser.nextText()));
                            }
                            if ("sex".equals(parser.getName())) {
                                student.setSex(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("student".equals(parser.getName())) {
                            list = new ArrayList<Student>();
                            list.add(student);
                           // Log.i("520xml", "**************" + list);
                            student = null;
                        }
                        break;
                }

                evenType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
