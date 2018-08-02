package com.LY.project.Utils;

/**
 * Created by linchujie on 2017/9/25.
 */

public class isEmpty {
    static public boolean StringIsEmpty(String string){
        if(string!=null&&!string.equals("")){
            return false;
        }
        else{
            return true;
        }
    }
}
