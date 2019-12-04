package com.peekay.aamirice.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.peekay.aamirice.R;

public class AboutFragment extends Fragment {
    private View view;
    ImageView imageView_icon;
    int i = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_about_fragment, null);
        imageView_icon = view.findViewById(R.id.img_icon);
        imageView_icon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (i==0){
                    i=1;
                    Toast.makeText(getContext(), "再长按一次退出", Toast.LENGTH_LONG).show();
                }else {
                    getActivity().onBackPressed();
                }
                return false;
            }
        });
        return view;
    }
}
