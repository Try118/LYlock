package com.diko.project.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * =====作者=====
 * lcj yx
 * =====时间=====
 * 2018/4/14.
 */
public class RetrofitUtils {

    private static final String ObjectUrl = "http://120.77.211.48/WooLock.php/Home/";
    private static Retrofit retrofit = null;
    private static IRetrofitServer iServer;

    public static IRetrofitServer getInstance() {
        if (retrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(ObjectUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    iServer = retrofit.create(IRetrofitServer.class);
                }
            }
        }
        return iServer;
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(String key, List<String> filePaths) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.size());
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public static RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    public interface IRetrofitServer {
        //登录接口
        @Multipart
        @POST("User/login")
        Call<ResponseBody> login(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //验证手机号是否存在接口
        @Multipart
        @POST("User/isexist")
        Call<ResponseBody> isexist(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //获取账号所拥有的所有门锁接口
        @Multipart
        @POST("lock/ReadAllLock")
        Call<ResponseBody> ReadAllLock(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //修改相对应门锁的地址
        @Multipart
        @POST("Lock/SetLockAddress")
        Call<ResponseBody> SetLockAddress(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //修改相对应门锁的名称
        @Multipart
        @POST("lock/changeLockName")
        Call<ResponseBody> changeLockName(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //发送密码
        @Multipart
        @POST("Lock/GetLockPassword")
        Call<ResponseBody> GetLockPassword(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //发送权限的接口
        @Multipart
        @POST("lock/GiveLock")
        Call<ResponseBody> GiveLock(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //获得授权信息的接口
        @Multipart
        @POST("lock/ReadGive")
        Call<ResponseBody> ReadGive(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //删除授权的接口
        @Multipart
        @POST("lock/cancelGive")
        Call<ResponseBody> cancelGive(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

        //查看开锁记录
        @Multipart
        @POST("Lock/GetOpenLog")
        Call<ResponseBody> GetOpenLog(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
//        //新建入库
//        @Multipart
//        @POST("goods/createGoods")
//        Call<ResponseBody> createGoods(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
//
//        //新建入库1
//        @Multipart
//        @POST("goods/addGoods")
//        Call<ResponseBody> addGoods(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
//
//        //新建出库
//        @Multipart
//        @POST("goods/deleteGoods")
//        Call<ResponseBody> deleteGoods(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
//
//        //显示物资流水
//        @Multipart
//        @POST("goods/showAll")
//        Call<ResponseBody> showList(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
//
//        //获取入库记录
//        @Multipart
//        @POST("goods/recordIn")
//        Call<ResponseBody> recordIn(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
//
//        //获取出库记录
//        @Multipart
//        @POST("goods/recordOut")
//        Call<ResponseBody> recordOut(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
//
//        //将单位注册页面的数据存进数据表
//        @Multipart
//        @POST("register/unit_register")
//        Call<ResponseBody> unit_register(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
//
//        //将个人注册页面的开户许可存进数据表
//        @Multipart
//        @POST("register/personal_register")
//        Call<ResponseBody> personal_register(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
    }
}
