package tools;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.peekay.aamirice.R;

public class SetTheme {
    Context context;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;

    public SetTheme(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public void initSwipe(String swipe) {
        int color = Color.parseColor("#009688");
        switch (swipe) {
            case "null":
                color = Color.parseColor("#009688");
                break;
            case "red":
                color = Color.parseColor("#DB4437");
                break;
            case "purple":
                color = Color.parseColor("#673AB7");
                break;
            case "ori":
                color = Color.parseColor("#FF5722");
                break;
            case "green":
                color = Color.parseColor("#0F9D58");
                break;
            case "blue":
                color = Color.parseColor("#03A9F4");
                break;
            case "dark":
                color = Color.parseColor("#282828");
                break;
            case "pink":
                color = Color.parseColor("#FA7298");
                break;
            case "blue1":
                color = Color.parseColor("#3F51B5");
                break;
            case "brown":
                color = Color.parseColor("#795547");
                break;
            case "gray":
                color = Color.parseColor("#607D8B");
                break;
            case "huaqing":
                color = Color.parseColor("#2376b7");
                break;
            case "danfanlan":
                color = Color.parseColor("#0f95b0");
                break;
            case "meidielv":
                color = Color.parseColor("#12aa9c");
                break;
            case "huaye":
                color = Color.parseColor("#F7C242");
                break;
            case "lv":
                color = Color.parseColor("#227D51");
                break;
        }
        swipeRefreshLayout.setColorSchemeColors(color);
    }

    public SetTheme(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    public void initTheme(String theme) {
        String name = "#009688";
        switch (theme) {
            case "null":
                context.setTheme(R.style.AppTheme);
                name = "#009688";
                break;
            case "red":
                context.setTheme(R.style.red);
                name = "#DB4437";
                break;
            case "purple":
                context.setTheme(R.style.purple);
                name = "#673AB7";
                break;
            case "ori":
                context.setTheme(R.style.ori);
                name = "#FF5722";
                break;
            case "green":
                context.setTheme(R.style.green);
                name = "#0F9D58";
                break;
            case "blue":
                context.setTheme(R.style.blue);
                name = "#03A9F4";
                break;
            case "dark":
                context.setTheme(R.style.dark);
                name = "#282828";
                break;
            case "pink":
                context.setTheme(R.style.pink);
                name = "#FA7298";
                break;
            case "blue1":
                context.setTheme(R.style.blue1);
                name = "#3F51B5";
                break;
            case "brown":
                context.setTheme(R.style.brown);
                name = "#795547";
                break;
            case "gray":
                context.setTheme(R.style.gray);
                name = "#607D8B";
                break;
            case "huaqing":
                context.setTheme(R.style.huaqing);
                name = "#2376b7";
                break;
            case "danfanlan":
                context.setTheme(R.style.danfanlan);
                name = "#0f95b0";
                break;
            case "meidielv":
                context.setTheme(R.style.meidielv);
                name = "#12aa9c";
                break;
            case "huaye":
                context.setTheme(R.style.huaye);
                name = "#F7C242";
                break;
            case "lv":
                context.setTheme(R.style.lv);
                name = "#227D51";
                break;
        }
        //判断是否为安卓4.4以上版本用户，如果是，添加沉浸状态栏
        int color = Color.parseColor(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setNavigationBarColor(color);
            window.setStatusBarColor(color);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
    }
}
