package com.peekay.aamirice.fragment;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.peekay.aamirice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tools.MyOpenHelper;


public class PageMusic extends Fragment {
    View view;
    ListView listView_songs;
    MyOpenHelper myOpenHelper;
    List<String> name = new ArrayList<>();
    List<String> link = new ArrayList<>();
    MediaPlayer mediaPlayer = new MediaPlayer();
    TextView textView_musictitle, textView_star, textView_end, textView_one, textView_tips;
    SeekBar seekBar_time;
    ImageButton imageButton_star, imageButton_left, imageButton_right;
    int s, mi, sec;
    String mi1, sec1;
    int index = 0;
    ScheduledExecutorService scheduledExecutorService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pager_music, null);
        initView();
        return view;
    }

    public void initView() {
        textView_tips = view.findViewById(R.id.tv_tips_music);
        textView_tips.setSelected(true);
        listView_songs = view.findViewById(R.id.lv_songs);
        textView_musictitle = view.findViewById(R.id.tv_musictitle);
        seekBar_time = view.findViewById(R.id.seek_time);
        textView_star = view.findViewById(R.id.tv_time_star);
        textView_end = view.findViewById(R.id.tv_time_end);
        textView_one = view.findViewById(R.id.tv_one);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.music);
        textView_one.startAnimation(animation);
        imageButton_left = view.findViewById(R.id.btn_left);
        imageButton_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 0 && mediaPlayer.isPlaying()) {
                    Toast.makeText(getContext(), "已经是第一首歌了", Toast.LENGTH_LONG).show();
                } else {
                    if (index - 1 < 0) {
                        index = 0;
                    } else {
                        index = index - 1;
                    }
                    new PlayMusic().execute();
                }

            }
        });
        imageButton_right = view.findViewById(R.id.btn_right);
        imageButton_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == link.size() - 1 && mediaPlayer.isPlaying()) {
                    Toast.makeText(getContext(), "已经是最后一首歌了", Toast.LENGTH_LONG).show();
                } else {
                    if (index + 1 >= link.size()) {
                        index = link.size() - 1;
                    } else {
                        index = index + 1;
                    }
                    new PlayMusic().execute();
                }
            }
        });
        imageButton_star = view.findViewById(R.id.btn_star);
        imageButton_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    imageButton_star.setImageResource(R.drawable.aw8);
                    mediaPlayer.pause();
                } else {
                    imageButton_star.setImageResource(R.drawable.aw7);
                    mediaPlayer.start();
                }
            }
        });
        listView_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == index && mediaPlayer.isPlaying()) {
                    Toast.makeText(getActivity(), "已经在播放这首歌", Toast.LENGTH_LONG).show();
                } else {
                    index = position;
                    new PlayMusic().execute();
                }
            }
        });
        seekBar_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        myOpenHelper = new MyOpenHelper(getContext());
        Cursor cursor = myOpenHelper.getAllMusic();
        while (cursor != null & cursor.moveToNext()) {
            name.add(cursor.getString(cursor.getColumnIndex("MUSICNAME")));
            link.add(cursor.getString(cursor.getColumnIndex("LINK")));
        }
        listView_songs.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, name));
    }

    @Override
    public void onDestroyView() {
        scheduledExecutorService.shutdown();
        super.onDestroyView();
    }

    //异步播放网络音乐
    class PlayMusic extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //加载时禁止点击
//            imageButton_left.setEnabled(false);
//            imageButton_right.setEnabled(false);
//            listView_songs.setEnabled(false);
//            Toast.makeText(getContext(),"正在加载音乐中",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(link.get(index)));
            mediaPlayer.start();
            s = mediaPlayer.getDuration();
            mi = s / 1000 / 60;
            sec = s / 1000 % 60;
            if (mi < 10) mi1 = "0" + mi;
            else mi1 = mi + "";
            if (sec < 10) sec1 = "0" + sec;
            else sec1 = sec + "";
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView_musictitle.setText(name.get(index));
            imageButton_star.setImageResource(R.drawable.aw7);
            textView_one.setText(textView_musictitle.getText().toString().substring(0, 1));//获取标题第一个文本
            textView_end.setText(mi1 + ":" + sec1);
            seekBar_time.setMax(mediaPlayer.getDuration());
//            imageButton_left.setEnabled(true);
//            imageButton_right.setEnabled(true);
//            listView_songs.setEnabled(true);
        }
    }

    //播放音乐
    public void playMusic() {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(link.get(index)));
        textView_musictitle.setText(name.get(index));
        imageButton_star.setImageResource(R.drawable.aw7);
        textView_one.setText(textView_musictitle.getText().toString().substring(0, 1));//获取标题第一个文本
        mediaPlayer.start();
        s = mediaPlayer.getDuration();
        mi = s / 1000 / 60;
        sec = s / 1000 % 60;
        if (mi < 10) mi1 = "0" + mi;
        else mi1 = mi + "";
        if (sec < 10) sec1 = "0" + sec;
        else sec1 = sec + "";
        textView_end.setText(mi1 + ":" + sec1);
        seekBar_time.setMax(mediaPlayer.getDuration());
    }

    @Override
    public void onStart() {
        super.onStart();
        s = mediaPlayer.getDuration();
        mi = s / 1000 / 60;
        sec = s / 1000 % 60;
        if (mi < 10) mi1 = "0" + mi;
        else mi1 = mi + "";
        if (sec < 10) sec1 = "0" + sec;
        else sec1 = sec + "";
        textView_end.setText(mi1 + ":" + sec1);
        seekBar_time.setMax(mediaPlayer.getDuration());
        textView_musictitle.setText(name.get(index));
        textView_one.setText(textView_musictitle.getText().toString().substring(0, 1));//获取标题第一个文本
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        s = mediaPlayer.getCurrentPosition();
                        mi = s / 1000 / 60;
                        sec = s / 1000 % 60;
                        if (mi < 10) mi1 = "0" + mi;
                        else mi1 = mi + "";
                        if (sec < 10) sec1 = "0" + sec;
                        else sec1 = sec + "";
                        textView_star.setText(mi1 + ":" + sec1);
                        seekBar_time.setProgress(mediaPlayer.getCurrentPosition());
                        if (!mediaPlayer.isPlaying()) {
                            imageButton_star.setImageResource(R.drawable.aw8);
                        } else {
                            imageButton_star.setImageResource(R.drawable.aw7);
                        }
                    }
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    //TODO 解析歌词未完成，也可能不会完成
    public void getLRC() {
//        String lcr = link.get(index).replace("song/media/outer/url?", "api/song/lyric?os=pc&lv=-1&kv=-1&tv=-1&");
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        Request request = new Request.Builder().url(lcr).get()
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36")
//                .header("Cache-Control", "no-cache")
//                .build();
//        Log.d("sss123", "getLRC: " + lcr);
//
//        String s = null;
//        try {
//            s = client.newCall(request).execute().body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
