package com.example.previous_dd.electronic_dictionary;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost fragmentTabHost;
    private String texts[]=
            {
            "1",
            "2",
            "3"};
    private int imageButton[] =
            {
            R.drawable.button1,
            R.drawable.button2,
            R.drawable.button3
    };
    private Class fragmentArray[] =
            {
             FragmentPage1.class,
             FragmentPage2.class,
             FragmentPage3.class
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, this.getSupportFragmentManager(),R.id.maincontent);

        for (int i = 0; i < texts.length; i++) {
            TabHost.TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));
            fragmentTabHost.addTab(spec, fragmentArray[i], null);
        }
        DbUtils dbUtils = DbUtils.create(this,"mydb");
        Cidian user = new Cidian("Hello","你好"); //这里需要注意的是User对象必须有id属性，或者有通过@ID注解的属性
        try {
            dbUtils.save(user);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private View getView(int i) {
        View view=View.inflate(MainActivity.this, R.layout.caidan, null);

        //取得布局对象
        ImageView imageView=(ImageView) view.findViewById(R.id.image);

        //设置图标
        imageView.setImageResource(imageButton[i]);
        return view;
    }
}
