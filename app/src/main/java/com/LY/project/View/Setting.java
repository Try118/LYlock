package com.LY.project.View;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Adapter.MyLockAdapter;
import com.LY.project.Controller.LockController;
import com.LY.project.CustomView.MyFastMenuBar;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.Module.ReadAllLock;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import customview.ConfirmDialog;
import feature.Callback;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import util.UpdateAppUtils;

/**
 * Created by YX_PC on 2018/4/10.
 * 门锁列表设置
 */

public class Setting extends BaseActivity implements MyFastMenuBar.onMenuBarClickListener {
    private MyFastMenuBar correct_password;//修改密码
    private MyFastMenuBar gesture;//手势密码
    private MyFastMenuBar version;//版本更新
    //    private MyFastMenuBar language;//语言
    private MyFastMenuBar about;//关于我们
    private MyFastMenuBar question;//常见问题
    private MyFastMenuBar logout;//退出登录
    private TextView correct_back;//返回键

    private String newcode = null; //最新版本号
    private int code; //当前版本号
    private String apkPath = "https://www.vooloc.com/Public/upload/apk/wule_apk.apk";//下载apk的路径

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews() {
        correct_password = (MyFastMenuBar) findView(R.id.correct_password);
        gesture = (MyFastMenuBar) findView(R.id.gesture);
        version = (MyFastMenuBar) findView(R.id.version);
//        language = (MyFastMenuBar) findView(R.id.language);
        about = (MyFastMenuBar) findView(R.id.about);
        question = (MyFastMenuBar) findView(R.id.question);
        logout = (MyFastMenuBar) findView(R.id.logout);
        correct_back = (TextView) findView(R.id.correct_back);
    }

    @Override
    public void initListener() {
        correct_password.setOnMenuBarClickListener(this);
        gesture.setOnMenuBarClickListener(this);
        version.setOnMenuBarClickListener(this);
//        language.setOnMenuBarClickListener(this);
        about.setOnMenuBarClickListener(this);
        question.setOnMenuBarClickListener(this);
        logout.setOnMenuBarClickListener(this);
        correct_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.correct_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onMenuBarClick(MyFastMenuBar view) {
        switch (view.getId()) {
            case R.id.correct_password:
                startActivity(VerifyRegistration.class);
                break;
            case R.id.gesture:
                startActivity(GestureSetting.class);
                break;
            case R.id.version:
                init();
                break;
//            case R.id.language:
//                break;
            case R.id.about:
                ShowDialog();
                break;
            case R.id.question:
                break;
            case R.id.logout:
                startActivity(Login.class);
                break;
            default:
                break;
        }
    }

    /**
     * 权限检查
     */
    private void checkAndUpdate() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            realUpdate();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                realUpdate();
            } else {//申请权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    /**
     * 此处更新仅仅靠版本名字的区别来做判定，也是一个bug吧。
     */
    private void realUpdate() {
        String vername = null;
        vername = getAPPLocalVersion(this);
        if (!vername.equals(newcode)) {
            UpdateAppUtils.from(this)
                    .serverVersionCode(10000)
                    .serverVersionName(newcode)
                    .apkPath(apkPath)
                    .updateInfo("1.修复若干bug\n2.美化部分页面\n3.增加微信支付方式")
                    .update();
        } else {
            showToast("当前为最新版本号");
        }

    }

    /**
     * 网络获取当前服务前的版本号
     */
    private void init() {
        LockController lockController = new LockController(Setting.this);
        List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("newco", RetrofitUtils.convertToRequestBody("1"));
        lockController.getApkVersion(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                newcode = String.valueOf(success);
                checkAndUpdate();
            }

            @Override
            public void onError(String error) {
                showToast(error);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 关于我们
     */
    public void ShowDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon)
                .setTitle("温馨提醒")
                .setMessage("\n深圳市物勒智能科技有限公司\n\n4008-656-256")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 获取apk版本号
     *
     * @param ctx
     * @return
     */
    private String getAPPLocalVersion(Context ctx) {
        int localVersionCode = 0;
        String versionName = null;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            localVersionCode = info.versionCode; // 版本号
            versionName = info.versionName; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            return versionName;
        }
    }

    /**
     * 权限请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    realUpdate();
                } else {
                    new ConfirmDialog(this, new Callback() {
                        @Override
                        public void callback(int position) {
                            if (position == 1) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                                startActivity(intent);
                            }
                        }
                    }).setContent("暂无读写SD卡权限\n是否前往设置？").show();
                }
                break;
        }

    }

}
