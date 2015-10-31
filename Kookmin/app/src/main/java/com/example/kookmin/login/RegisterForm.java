package com.example.kookmin.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kookmin.R;
import com.example.kookmin.mainactivity.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hyung-Jun on 2015-10-30.
 */
public class RegisterForm extends Activity {
    ImageButton joinButton;
    EditText uName,uMajor,uCode,uPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerform_layout);
        joinButton = (ImageButton)findViewById(R.id.join_btn);
        uName = (EditText)findViewById(R.id.editText1);
        uMajor = (EditText)findViewById(R.id.editText2);
        uCode = (EditText)findViewById(R.id.editText3);
        uPw = (EditText)findViewById(R.id.editText4);


        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendPost().execute();
            }
        });
    }
    //eamil과 password와 mirror 값이 null이 아닐때만 서버 DB에 전송해 중복값이 있는지 확인한다.
    private class SendPost extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void...unused){
            String content = executeClient();
            return content;
        }
        protected void onPostExecute(String result){
            //모든 작업을 마치고 실행할 일.
            Intent intent = new Intent(RegisterForm.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        //실제 전송하는 부분
        public String executeClient(){
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            String tmp = "10";
            post.add(new BasicNameValuePair("u_id", uCode.getText().toString()));
            post.add(new BasicNameValuePair("u_pwd", uPw.getText().toString()));
            post.add(new BasicNameValuePair("u_code", uCode.getText().toString()));
            post.add(new BasicNameValuePair("u_name", uName.getText().toString()));
            post.add(new BasicNameValuePair("u_major", uMajor.getText().toString()));

            HttpClient client = new DefaultHttpClient();
            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);
            // Post객체 생성
            //HttpPost httpPost = new HttpPost("http://10.30.34.238/test.php");
            HttpPost httpPost = new HttpPost("http://gudwns999.cafe24.com/TEST/insertDBTest.php");
            //      HttpPost httpPost = new HttpPost("http://52.74.13.142/test1.php");
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
                httpPost.setEntity(entity);
                client.execute(httpPost);
                return EntityUtils.getContentCharSet(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}