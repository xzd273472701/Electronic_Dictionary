package com.example.previous_dd.electronic_dictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class FragmentPage1 extends Fragment {
    private ImageButton btnReconizer;
    private EditText editText;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private Button biangeng;

    private String result="";
    private TextView tv;
    private EditText et;
    private Button btn;
    private String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";//请求的网址
    private String q = "";//请求翻译query
    private String from = "auto";//翻译源语言
    private String to;//译文语言
    private String appid = "20170619000059345";//APP ID
    private String salt = "512";//随机数
    private String secretkey = "Ru3wLDae90YZ_Z1J6lk8";//密钥
    private JSONObject js;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View V= inflater.inflate(R.layout.activity_fragment_page1, container,false);
        btnReconizer= (ImageButton) V.findViewById(R.id.btnRecognizer);
        editText = (EditText) V.findViewById(R.id.et_text);
        tv = (TextView) V.findViewById(R.id.tv_text);
        et = (EditText) V.findViewById(R.id.et_text);
        btn = (Button) V.findViewById(R.id.btn);
        biangeng = (Button) V.findViewById(R.id.biangeng);
        to = "en";
        biangeng.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CharSequence[] items = {
                        "中文",
                        "日语",
                        "韩语",
                        "法语",
                        "泰语",
                        "俄语",
                        "德语",
                        "意大利语",
                        "希腊语"
                };
                imageChooseItem(items);

            }


        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                q = et.getText().toString();
                translate();
            }


        });
        btnReconizer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try{
                    //通过Intent传递语音识别的模式，开启语音
                    Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    //语言模式和自由模式的语音识别
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    //提示语音开始
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "开始语音");
                    //开始语音识别
                    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
                }catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "找不到语音设备", 1).show();
                }
            }
        });
        return V;
    }

    private void translate() {//POST请求
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("q", q);//请求翻译query
        params.addQueryStringParameter("from", from);//翻译源语言
        params.addQueryStringParameter("to", to);//译文语言
        params.addQueryStringParameter("appid", appid);//APP ID
        params.addQueryStringParameter("salt", salt);//随机数
        params.addQueryStringParameter("sign", MD5.getMD5(appid+q+salt+secretkey));//签名
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.POST, url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(), "错误",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        // TODO Auto-generated method stub
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = arg0.result;
                        handler.sendMessage(msg);
                    }
                });
    }
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == 1) {
                String r = (String) msg.obj;
                    System.out.println(r);
                    try {
                        js = new JSONObject(r);
                        JSONArray value = js.getJSONArray("trans_result");
                    JSONObject child = null;
                    for(int i=0;i<value.length();i++){
                        child = value.getJSONObject(i);
                        result = child.getString("dst");
                        tv.setText(result);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    try {
                        String errorCode = js.getString("error_code");
                              if("52001".equals(errorCode))
                              {
                            Toast.makeText(getActivity(), "请求超时，请重试",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if("52002".equals(errorCode))
                        {
                            Toast.makeText(getActivity(), "系统错误，请重试",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if("52003".equals(errorCode))
                        {
                            Toast.makeText(getActivity(), "未授权用户，请检查您的appid是否正确",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if("54000".equals(errorCode))
                        {
                            Toast.makeText(getActivity(), "必填参数为空，请检查是否少传参数",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if("58000".equals(errorCode))
                        {
                            Toast.makeText(getActivity(), "客户端IP非法，请检查您填写的IP地址是否正确可修改您填写的服务器IP地址",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if("54001".equals(errorCode))
                        {
                            Toast.makeText(getActivity(), "签名错误，请检查您的签名生成方法",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if("54003".equals(errorCode))
                        {
                            Toast.makeText(getActivity(), "访问频率受限，请降低您的调用频率",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if("58001".equals(errorCode))
                        {
                            Toast.makeText(getActivity(), "译文语言方向不支持，请检查译文语言是否在语言列表里",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if("54004".equals(errorCode))
                        {
                            Toast.makeText(getActivity(), "账户余额不足，请前往管理控制台为账户充值",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        }
    };

    public void imageChooseItem(CharSequence[] items )
    {
        android.app.AlertDialog imageDialog =
                new android.app.AlertDialog.Builder(getContext()).setTitle("选择").setItems(items,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {

                        if( item == 0 )
                        {
                            to = "zh";
                            biangeng.setText("中文");
                        }

                        else if( item == 1 )
                        {
                            to = "jp";
                            biangeng.setText("日语");
                        }
                        else if( item == 2 )
                        {
                            to = "kor";
                            biangeng.setText("韩语");
                        }
                        else if( item == 3 )
                        {
                            to = "fra";
                            biangeng.setText("法语");
                        }
                        else if( item == 4 )
                        {
                            to = "th";
                            biangeng.setText("泰语");
                        }
                        else if( item == 5 )
                        {
                            to = "ru";
                            biangeng.setText("俄语");
                        }
                        else if( item == 6 )
                        {
                            to = "de";
                            biangeng.setText("德语");
                        }
                        else if( item == 7 )
                        {
                            to = "it";
                            biangeng.setText("意大利语");
                        }
                        else if( item == 8 )
                        {
                            to = "el";
                            biangeng.setText("希腊语");
                        }
                    }}).create();

        imageDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        //回调获取从谷歌得到的数据
        if(requestCode==VOICE_RECOGNITION_REQUEST_CODE && resultCode==RESULT_OK){
            //取得语音的字符
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String resultString="";
            for(int i=0;i<results.size();i++){
                resultString+=results.get(i);
            }
            editText.setText(resultString);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
