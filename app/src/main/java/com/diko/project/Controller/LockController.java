package com.diko.project.Controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.diko.project.Manager.InterfaceManger;
import com.diko.project.Module.Login;
import com.diko.project.Module.ReadAllLock;
import com.diko.project.R;
import com.diko.project.Utils.RetrofitUtils;
import com.diko.project.View.AddLock;
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

public class LockController{

    private static Context context;

    public LockController(Context context) {
        this.context=context;
    }

    /**
     * 门锁信息列表模块
     */

    public static void ReadAllLock(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().ReadAllLock(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    listener.onError(context.getString(R.string.server_error)+ response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    Object object = body;
                    if (!body.contains("error")) {
                        listener.onSuccess(object);
                    } else {
                        listener.onError(context.getString(R.string.unknow_error));
                    }
                } catch (Exception e) {
                    listener.onError(e.toString());
                    e.printStackTrace();
                }
                listener.onComplete();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (listener == null) {
                    return;
                }
                Log.e("onFailure", t.toString());
                if (t.toString().contains("ConnectException")) {
                    listener.onError(context.getString(R.string.no_internet));
                } else {
                    listener.onError(context.getString(R.string.network_anomaly));
                }
                listener.onComplete();
            }
        });
    }
}
