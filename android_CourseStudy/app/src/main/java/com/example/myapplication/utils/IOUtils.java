package com.example.myapplication.utils;

import android.util.Xml;

import com.example.myapplication.entity.Practise;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    public static String convert(InputStream is,String encode){
        try{
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,encode));
            while ((line = reader.readLine())!= null){
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    //读取xml习题
    public static List<Practise> getXmlContents(InputStream is) throws Exception{
        List<Practise> details = null;
        Practise detail = null;
        //1创建一个解析器
        XmlPullParser parser = Xml.newPullParser();
        //2设置输入源
        parser.setInput(is,"UTF-8");
        //3根据EventType类型，判断节点名称，解析数据，将输入放入集合
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            String nodeName = parser.getName();
            switch (eventType){
                case XmlPullParser.START_TAG:
                    if ("infos".equals(nodeName)){
                        details = new ArrayList<>();
                    } else if ("exercises".equals(nodeName)){
                        detail = new Practise();
                        String ids = parser.getAttributeValue(0);
                        detail.setSubjectId(Integer.parseInt(ids));
                    } else if ("subject".equals(nodeName)&&detail!=null){
                        detail.setSubject(parser.nextText());
                    } else if ("a".equals(nodeName)&&detail!=null){
                        detail.setSelectA(parser.nextText());
                    } else if ("b".equals(nodeName)&&detail!=null){
                        detail.setSelectB(parser.nextText());
                    } else if ("c".equals(nodeName)&&detail!=null){
                        detail.setSelectC(parser.nextText());
                    } else if ("d".equals(nodeName)&&detail!=null){
                        detail.setSelectD(parser.nextText());
                    } else if ("answer".equals(nodeName)&&detail!=null){
                        detail.setAnswer(Integer.parseInt(parser.nextText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("exercises".equals(nodeName)&&details!=null){
                        details.add(detail);
                        detail = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return details;
    }
}
