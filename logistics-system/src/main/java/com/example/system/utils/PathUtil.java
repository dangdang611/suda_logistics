package com.example.system.utils;

import com.example.system.componet.Constant;
import com.example.system.vo.unify.Point;

public class PathUtil {

    //获取省会城市
    public static Point getProCpt(String str){
        int i = 0;
        for (i = 0 ; i < Constant.mapList.length ; i ++ ){
            int j = 0;
            String location = Constant.mapList[i].getLocation();
            while (j < location.length() && j < str.length() && location.charAt(j) == str.charAt(j))j++;
            if (j > 0)return Constant.mapList[i];
        }
        return Constant.mapList[0];
    }

}
