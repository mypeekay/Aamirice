package com.peekay.aamirice;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.peekay.aamirice.R;
import com.peekay.aamirice.adapter.ThemeAdapter;
import com.peekay.aamirice.bean.Theme;

import java.util.ArrayList;

public class SelectTheme extends Fragment {
    View view;
    ArrayList<Theme> themes = new ArrayList<>();
    ListView listView;
    ThemeAdapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final String RED = "热情似火——热情特别高，就像火一样。";
    final String ORI = "橙黄橘绿——指秋季景物。";
    final String PURPLE = "紫气东来——旧时比喻吉祥的征兆。";
    final String GREEN = "绿意盎然——鲜花盛开，春意盎然。";
    final String BLUE = "蓝田生玉——旧时比喻贤父生贤子。 比喻名门出贤子弟。";
    final String DARK = "明人不说暗话——光明正大的人说实在话，不转弯抹角。";
    final String PINK = "红粉青蛾——原指红色的铅粉，为女子化妆用品，后借指美女。";
    final String BLUE1 = "青蓝冰水——“青出于蓝，冰寒于水”的略语。比喻学生超过老师或后人胜过前人。";
    final String BROWN = "传棕接代——emmmmmmm,不知道写啥了";
    final String GRAY = "灰常美丽——意思就是你非常漂亮！";
    final String DEF = "平步青云——比喻不费气力，一下子就达到了很高的地位。";
    final String HUAQING = "花青——中国传统色";
    final String DANFANLAN = "胆矾蓝——中国传统色";
    final String MEIDIELV = "美蝶绿——中国传统色";
    final String HUAYE = "花叶——传统色";
    final String LV = "绿——传统色";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.select_theme, null);
        listView = view.findViewById(R.id.lv_theme);
        sharedPreferences = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
        initThemes();
        adapter = new ThemeAdapter(getContext(), R.layout.theme_items, themes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = themes.get(position).getName();
                editor = sharedPreferences.edit();
                switch (name) {
                    case DEF:
                        editor.putString("theme", "null");
                        editor.apply();
                        break;
                    case RED:
                        editor.putString("theme", "red");
                        editor.apply();
                        break;
                    case ORI:
                        editor.putString("theme", "ori");
                        editor.apply();
                        break;
                    case PURPLE:
                        editor.putString("theme", "purple");
                        editor.apply();
                        break;
                    case GREEN:
                        editor.putString("theme", "green");
                        editor.apply();
                        break;
                    case BLUE:
                        editor.putString("theme", "blue");
                        editor.apply();
                        break;
                    case DARK:
                        editor.putString("theme", "dark");
                        editor.apply();
                        break;
                    case PINK:
                        editor.putString("theme", "pink");
                        editor.apply();
                        break;
                    case BLUE1:
                        editor.putString("theme", "blue1");
                        editor.apply();
                        break;
                    case BROWN:
                        editor.putString("theme", "brown");
                        editor.apply();
                        break;
                    case GRAY:
                        editor.putString("theme", "gray");
                        editor.apply();
                        break;
                    case HUAQING:
                        editor.putString("theme", "huaqing");
                        editor.apply();
                        break;
                    case DANFANLAN:
                        editor.putString("theme", "danfanlan");
                        editor.apply();
                        break;
                    case MEIDIELV:
                        editor.putString("theme", "meidielv");
                        editor.apply();
                        break;
                    case HUAYE:
                        editor.putString("theme", "huaye");
                        editor.apply();
                        break;
                    case LV:
                        editor.putString("theme", "lv");
                        editor.apply();
                        break;
                }
                getActivity().recreate();
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void initThemes() {
        themes.add(new Theme(Color.parseColor("#009688"), DEF));
        themes.add(new Theme(Color.parseColor("#0F9D58"), GREEN));
        themes.add(new Theme(Color.parseColor("#673AB7"), PURPLE));
        themes.add(new Theme(Color.parseColor("#03A9F4"), BLUE));
        themes.add(new Theme(Color.parseColor("#FF5722"), ORI));
        themes.add(new Theme(Color.parseColor("#DB4437"), RED));
        themes.add(new Theme(Color.parseColor("#FA7298"), PINK));
        themes.add(new Theme(Color.parseColor("#3F51B5"), BLUE1));
        themes.add(new Theme(Color.parseColor("#795547"), BROWN));
        themes.add(new Theme(Color.parseColor("#607D8B"), GRAY));
        themes.add(new Theme(Color.parseColor("#2376b7"), HUAQING));
        themes.add(new Theme(Color.parseColor("#0f95b0"), DANFANLAN));
        themes.add(new Theme(Color.parseColor("#12aa9c"), MEIDIELV));
        themes.add(new Theme(Color.parseColor("#F7C242"), HUAYE));
        themes.add(new Theme(Color.parseColor("#227D51"), LV));
        themes.add(new Theme(Color.parseColor("#282828"), DARK));
    }
}
