package com.example.kookmin.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kookmin.R;
import com.example.kookmin.mainactivity.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyung-Jun on 2015-10-30.
 */
public class LoginForm extends Activity {
    private static final String MY_DB = "my_db";
    EditText userCode;
    EditText userPw;
    ImageButton loginBtn;
    TextView registerBtn;

    //로그인 php
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(final Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.loginform_layout);
        userCode = (EditText)findViewById(R.id.userEmail_text);
        userPw = (EditText)findViewById(R.id.userPass_text);
        loginBtn = (ImageButton)findViewById(R.id.login_btn);
        registerBtn = (TextView)findViewById(R.id.registerTo_txt);
        //로그인 버튼을 눌렀을 시.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginForm.this, "",
                        "확인중입니다. 기다려주세요...", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                }).start();

            }
        });
        //회원 가입 버튼을 눌렀을 시.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원 가입 화면으로 넘어감.
                Intent intent = new Intent(LoginForm.this, RegisterForm.class);
                startActivity(intent);
                finish();
            }
        });

    }
    void login(){
        try{
            httpclient=new DefaultHttpClient();
//            httppost= new HttpPost("http://10.30.38.74/SmartMirror/check.php"); // make sure the url is correct. Test
            httppost= new HttpPost("http://gudwns999.cafe24.com/TEST/checkIDTest.php"); // make sure the url is correct. Test
            //add your data
            nameValuePairs = new ArrayList<NameValuePair>(2);
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
            nameValuePairs.add(new BasicNameValuePair("u_id",userCode.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("u_pwd",userPw.getText().toString().trim()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Execute HTTP Post Request
            response=httpclient.execute(httppost);
            // edited by James from coderzheaven.. from here....
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            final String[] inform=response.split(";");//Find / 전공/ 성별/ 학부생 / 닉네임

            System.out.println("Response : " + response);
            runOnUiThread(new Runnable() {
                public void run() {
                    //      tv.setText("Response from PHP : " + response);
                    //       tv.setText("Response from PHP : " + response+"ss"+inform[0]);
                    dialog.dismiss();
                }
            });
            if(inform[0].equalsIgnoreCase(" User Found")){

                runOnUiThread(new Runnable() {
                    public void run() {
                        SharedPreferences sp = getSharedPreferences(MY_DB, Context.MODE_PRIVATE);
                        //자동 로그인
                        SharedPreferences.Editor e1 = sp.edit();
                        e1.putString("userCode", inform[1]);
                        e1.putString("userName", inform[2]);
                        e1.putString("userMajor", inform[3]);
                        e1.putBoolean("loginCheck", true);
                        e1.commit();
                        Toast.makeText(LoginForm.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(LoginForm.this, MainActivity.class));
                finish();
            }else{
                showAlert();
            }
        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    public void showAlert(){
        LoginForm.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginForm.this);
                builder.setTitle("로그인 에러");
                builder.setMessage("아이디/비밀번호를 확인해주세요")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}