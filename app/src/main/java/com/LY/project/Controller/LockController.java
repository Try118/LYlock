package com.LY.project.Controller;

import android.content.Context;
import android.util.Log;

import com.LY.project.Manager.InterfaceManger;
import com.LY.project.Module.Login;
import com.LY.project.Module.activeLock;
import com.LY.project.Module.addLock;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;
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

public class LockController {

    private static Context context;

    public LockController(Context context) {
        this.context = context;
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
                    listener.onError(context.getString(R.string.server_error) + response.code());
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

    /**
     * 上传开锁记录
     */

    public static void OpenLock(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().OpenLock(map, parts);
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
                    Log.e("onResponse: body",body );
                    listener.onSuccess("");
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

    /**
     * 更新门锁电量
     */

    public static void UpdateLockPower(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().UpdateLockPower(map, parts);
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
                    Log.e("onResponse:body","213123");
                    Log.e("onResponse:body",body);
                    listener.onSuccess("");
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

    /**
     * 激活门锁
     */

    public static void activeLock(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().activeLock(map, parts);
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
                    Log.e("YXonResponse", body);

                    int code = jsonObject.getInt("code");
                    Log.e("activeLockonResponse", String.valueOf(code));
                    if (code == 1) {
                        listener.onSuccess(new Gson().fromJson(body, activeLock.class));
                    } else {
                        listener.onError("激活失败");
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


    /**
     * 添加门锁
     */

    public static void addLock(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().addLock(map, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (listener == null) {
                    return;
                }
                if (!response.isSuccessful() || response == null) {
                    listener.onError(context.getString(R.string.server_error) + response.code());
//                    listener.onError("服务器异常不是我");
                    return;
                }
                try {
                    String body = response.body().string();
                    JSONObject jsonObject = new JSONObject(body);
                    Log.e("YXonResponseaddLock", body);

                    int code = jsonObject.getInt("code");
                    Log.e("onResponse", String.valueOf(code));
                    if (code == 1) {
                        listener.onSuccess(new Gson().fromJson(body, addLock.class));
                    } else {
                        listener.onError("添加失败");
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

    /**
     * 上传恢复出厂密码
     */

    public static void SetRestore(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().SetRestore(map, parts);
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
                    Log.e("YXonResponseSetRestore", body);

                    int code = jsonObject.getInt("code");
                    Log.e("onResponse", String.valueOf(code));
                    if (code == 1) {
                        listener.onSuccess("");
                    } else {
                        listener.onError("失败");
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


    /**
     * 删除门锁
     */

    public static void deleteLock(Map<String, RequestBody> map, List<MultipartBody.Part> parts, final InterfaceManger.OnRequestListener listener) {
        Call<ResponseBody> call = RetrofitUtils.getInstance().deleteLock(map, parts);
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
                    Log.e("YXonResponse", body);

                    int code = jsonObject.getInt("code");
                    Log.e("activeLockonResponse", String.valueOf(code));
                    if (code == 1) {
                        listener.onSuccess(new Gson().fromJson(body, activeLock.class));
                    } else {
                        listener.onError("删除失败");
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
