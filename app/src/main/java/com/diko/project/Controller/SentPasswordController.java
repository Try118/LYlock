package com.diko.project.Controller;

import android.content.Context;
import android.util.Log;

import com.diko.project.Manager.InterfaceManger;
import com.diko.project.Module.GetLockPassword;
import com.diko.project.Module.Login;
import com.diko.project.R;
import com.diko.project.Utils.RetrofitUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jie on 2018/4/14.
 */

public class SentPasswordController {

    private static Context context;

    public SentPasswordController(Context context) {
        this.context = context;
    }
    /**
     * 发送密码
     */
    public static void GetLockPassword(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().GetLockPassword(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    listener.onError(context.getString(R.string.server_error) + response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    JSONObject jsonObject = new JSONObject(body);
                    Log.e("body：", body);

                    int code = jsonObject.getInt("code");
                    Log.e("onResponse", String.valueOf(code));
                    if (code == 1) {
                        listener.onSuccess(new Gson().fromJson(body, GetLockPassword.class));
                    } else {
                        listener.onError(context.getString(R.string.sendPassword_failed));
                    }
                } catch (Exception e) {
                    listener.onError(e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (listener == null) {
                    return;
                }
                Log.e("onFailure", t.toString());
                listener.onError(t.toString());
            }
        });
    }
}
