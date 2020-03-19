package com.shumile.springbootjpa;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        String mapList  =  "0:message_type,2:card_no,3:processing_code";
        if (mapList==null ||mapList.equals("")){
            //logger.error("配置文件中后端域的映射，配置有误:{}",,mapList);
            throw new RuntimeException("配置文件中后端域的映射，配置有误，请检查配置");

        }
        List<String> list = Arrays.asList(mapList.split(","));//根据逗号分隔转化为list
        List<Map<String,String>> maps = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (String keyValue : list) {
            String[] mapsStr = keyValue.split(":");
            if (mapsStr!=null && mapsStr.length==2){
                map.put(mapsStr[0], mapsStr[1]);
                maps.add(map);
            }
        }
        System.out.println(maps);
    }
}
