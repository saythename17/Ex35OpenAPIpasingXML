package com.icandothisallday2020.ex35openapipasingxml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> movies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
        adapter=new ArrayAdapter(this,R.layout.form_listview_item,movies);
        listView.setAdapter(adapter);
    }
    public void click(View view) {
    //res/xml/movies.xml-분석하여(parse) listView 의 데이터(ArrayList)에 추가
        Resources res=getResources();//res 창고관리자 소환
        XmlResourceParser xrp=res.getXml(R.xml.movies);//res 안 XML 문서를 읽어분석해주는 분석가 소환
        //분석가 객체(xrp)를 통해 xml 문서 분석
        try {
            xrp.next();//문서 커서 한 단위(Event) 이동
            int eventType=xrp.getEventType();//EventType---XmlResourcesParser 가 분석하는 단위
            String item="";
            while(eventType!=XmlResourceParser.END_DOCUMENT){//문서가 끝날때 까지
                switch (eventType){
                    case XmlResourceParser.START_DOCUMENT:
                        Toast.makeText(this,"Start parsing",Toast.LENGTH_SHORT).show();
                        break;
                    case XmlResourceParser.START_TAG:
                         //STAR_TAG 에 써있는 text 얻어오기
                         String name=xrp.getName();
                         if(name.equals("item")) item="";//영화 1개의 정보 시작 문자열
                         else if(name.equals("no")) item+="번호 : ";
                         else if(name.equals("title")) item+="제목 : ";
                         else if(name.equals("genre")) item+="장르 : ";
                         break;
                    case XmlResourceParser.TEXT:
                        item+=xrp.getText();//TEXT 의 문자열 얻어오기
                        break;
                    case XmlResourceParser.END_TAG:
                        String name2=xrp.getName();
                        if(name2.equals("no")|| name2.equals("title")) item+="\n";
                        else if(name2.equals("item")) {
                            //하나의 movie(item)이 끝났으므로 listView 가 보여주는 movies 에 추가
                            movies.add(item);
                            adapter.notifyDataSetChanged();
                        }
                }//switch....
                eventType = xrp.next();//커서를 옮김과 동시에 이벤트타입리턴
            }//while....
            Toast.makeText(this,"End parsing",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {e.printStackTrace();}
        adapter.notifyDataSetChanged();
    }
}









