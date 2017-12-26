package com.niuda.a3jidi.lib_base.base.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-12-26 15:27
 */

public abstract class BasePermissionActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    private static final String TAG = "BasePermissionActivity";

    @Override
    public abstract int initLayout();

    @Override
    public abstract void initView();

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(this, "权限添加成功", Toast.LENGTH_SHORT).show();
        }
    }


    //
//    // 权限添加
//    private void methodRequiresTwoPermission() {
//        int RC_CAMERA_AND_WIFI = 0;
//        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            //已经有权限，该做的事
//            // ...
//        } else {
//            //没有权限，现在请求
//            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限",RC_CAMERA_AND_WIFI, perms);
//        }
//    }

}
