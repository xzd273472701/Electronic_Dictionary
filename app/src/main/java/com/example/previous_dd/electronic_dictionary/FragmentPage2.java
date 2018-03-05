package com.example.previous_dd.electronic_dictionary;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class FragmentPage2 extends Fragment {
     private Button button;
     private EditText e1,e2;
     private String yy,zw;
     private DbUtils dbUtils;
     private ProgressWheel wheel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View V= inflater.inflate(R.layout.activity_fragment_page2, container,false);
        final Context context = this.getContext();
        button = (Button) V.findViewById(R.id.tijiao);
        e1 = (EditText) V.findViewById(R.id.yingyu);
        e2 = (EditText) V.findViewById(R.id.zhongwen);
        dbUtils = DbUtils.create(getContext(),"mydb");
        wheel = (ProgressWheel) V.findViewById(R.id.progress_wheel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yy = e1.getText().toString();
                zw = e2.getText().toString();
                wheel.setVisibility(View.VISIBLE);
                wheel.setBarColor(Color.BLUE);
                wheel.setProgress(0);
                try {
                    Cidian cidian = new Cidian(yy,zw);
                    dbUtils.save(cidian);
                    wheel.setProgress(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        wheel.setSpinSpeed(1/3f);
        wheel.setCallback(new ProgressWheel.ProgressCallback() {
            @Override
            public void onProgressUpdate(float progress)  {
                if (progress == 1.0f) {
                    wheel.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "存储完成", Toast.LENGTH_LONG).show();
                }
            }
        });
        return V;
    }


}
