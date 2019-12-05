package tools;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import okhttp3.OkHttpClient;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient.Builder builder=new OkHttpClient.Builder().addInterceptor(new LoggerInterceptor("sss123",true));
        OkHttpUtils.initClient(builder.build());
    }
}
