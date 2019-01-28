package com.example.x.memo.Utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class XPermissionUtil {

    private static String[] Permissions;

    private XPermissionUtil(){ }

    public static void init(String[] permissions){
        Permissions=permissions;
    }

    public static void check(Activity context) {
        List<String> mPermissionList = new ArrayList<>();
        for (String Permission : Permissions) {
            if (ContextCompat.checkSelfPermission(context, Permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(Permission);
            }
        }
        if (!mPermissionList.isEmpty()) {
            String[] permissions = mPermissionList.toArray(new String[0]);//将List转为数组
            ActivityCompat.requestPermissions(context, permissions, 1);
        }
    }

    public static boolean check(Activity activity,String permission){

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(activity,"app需要权限许可才能正常运行",Toast.LENGTH_SHORT).show();

            PermissionSettingPage.start(activity,false);
            return false;
        }
        else
        {
            return true;
        }
    }
}
