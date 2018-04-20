package com.diko.project.Controller;

import android.content.Context;
import android.util.Log;

import com.diko.project.Manager.InterfaceManger;
import com.diko.project.Module.Login;
import com.diko.project.R;
import com.diko.project.Utils.RetrofitUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jie on 2018/4/17.
 */

public class LockSetController {

    private static Context context;

    public LockSetController(Context context) {
        this.context = context;
    }

    /**
     * 修改门锁地址
     */
    public static void SetLockAddress(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().SetLockAddress(map, parts);
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
                    String body = response.body().string().toString();
                    JSONObject jsonObject = new JSONObject(body);
                    Log.e("onResponse", body);

                    int code = jsonObject.getInt("code");
                    Log.e("onResponse", String.valueOf(code));
                    if (code == 1) {
                        listener.onSuccess(body);
                    } else {
                        listener.onError(context.getString(R.string.correct_fault));
                    }
                } catch (Exception e) {
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

    /**
     * 修改门锁名称
     */
    public static void changeLockName(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().changeLockName(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    Log.e("response", String.valueOf(response));
                    listener.onError(context.getString(R.string.server_error) + response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    JSONObject jsonObject = new JSONObject(body);
                    Log.e("onResponsettt", body);

                    int code = jsonObject.getInt("code");
                    Log.e("onResponsettt", String.valueOf(code));
                    if (code == 1) {
                        listener.onSuccess(body);
                    } else {
                        listener.onError(context.getString(R.string.correct_fault));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listener.onComplete();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (listener == null) {
                    return;
                }
                if (t.toString().contains("ConnectException")) {
                    listener.onError(context.getString(R.string.no_internet));
                } else {
                    listener.onError(context.getString(R.string.network_anomaly));
                }
                Log.e("onFailure", t.toString());
                listener.onComplete();
            }
        });
    }

    /**
     * 获取授权内容
     */
    public static void ReadGive(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().ReadGive(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    Log.e("response", String.valueOf(response));
                    listener.onError(context.getString(R.string.server_error) + response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    Log.e("onResponse: ", body);
                    Object object = body;
                    if (!body.contains("error")) {
                        listener.onSuccess(object);
                    } else {
                        listener.onError(context.getString(R.string.unknow_error));
                    }
                } catch (Exception e) {
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

    /**
     * 删除授权门锁
     */
    public static void cancelGive(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().cancelGive(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    Log.e("response", String.valueOf(response));
                    listener.onError(context.getString(R.string.server_error) + response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    Log.e("onResponse: ", body);
                    Object object = body;
                    if (!body.contains("error")) {
                        listener.onSuccess(context.getString(R.string.delete_successful));
                    } else {
                        listener.onError(context.getString(R.string.unknow_error));
                    }
                } catch (Exception e) {
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

    /**
     * 查看开锁记录
     */
    public static void GetOpenLog(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().GetOpenLog(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    Log.e("responseabc", String.valueOf(response));
                    listener.onError(context.getString(R.string.server_error) + response.code());
                    return;
                }
                try {
                    String body = response.body().string();
                    Log.e("onResponsettta", body);
                    listener.onSuccess(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listener.onComplete();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (listener == null) {
                    return;
                }
                Log.e("onFailureabc", t.toString());
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
