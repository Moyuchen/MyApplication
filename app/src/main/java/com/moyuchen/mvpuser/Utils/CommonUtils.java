package com.moyuchen.mvpuser.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * User: 张亚博
 * Date: 2017-10-10 16:08
 * Description：
 */
public class CommonUtils {

    private static boolean loginStatic1;
    private static String str;

    public static void SHOWTOAST(final Activity activity, final String msg ){
        if (activity!=null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity,msg,Toast.LENGTH_LONG).show();
                }
            });
        }


    }
    public static int GetModdle(int size){
        int moddle=0;
        if (size%2==0) {
            moddle=size/2;
        }else {
            moddle=(size+1)/2;
        }
        return moddle;
    }
    public static void BaoCunLoginStatic(Context context,Boolean loginstatic,String name){
        if (context!=null) {
            SharedPreferences loginStatic = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = loginStatic.edit();
            edit.putBoolean("loginStatic",loginstatic).commit();
        }
    }
    public static void BaoCunString(Context context,String result,String name){
        if (context!=null) {
            SharedPreferences loginStatic = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = loginStatic.edit();
            edit.putString(name,result).commit();
        }
    }
    public static Boolean GetLoginStatic(Context context,String name){
        if (context!=null) {
            SharedPreferences loginStatic = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            loginStatic1 = loginStatic.getBoolean(name,false);
        }
        return loginStatic1;
    }
    public static String GetString(Context context,String name){
        if (context!=null) {
            SharedPreferences loginStatic = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            str = loginStatic.getString(name,null);
        }
        return str;
    }

    public static String FenGeString(String string,int number){
        String[] split = string.split("\\|");
        return split[number];
    }
    public static String[] FenGeString(String string){
        String[] split = string.split("\\|");
        return split;
    }
}
