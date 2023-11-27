package com.example.project_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Screen2 extends AppCompatActivity {

    private boolean isWebViewVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageView = findViewById(R.id.image1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen2.this, Screen2_vn.class);
                startActivity(intent);
            }
        });

        EditText editText_password = findViewById(R.id.password);
        editText_password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        EditText editText_rePassword = findViewById(R.id.rePassword);
        editText_rePassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText_rePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        WebView webView = findViewById(R.id.webView);
        Button Button2 = findViewById(R.id.button2);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Button2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username);
                EditText email = findViewById(R.id.email);
                EditText password = findViewById(R.id.password);
                EditText rePassword = findViewById(R.id.rePassword);
                String usrScript = "javascript:document.getElementById('username').value = '" + username.getText() + "';";
                String emailScript = "javascript:document.getElementById('email').value = '" + email.getText() + "';";
                String pwdScript = "javascript:document.getElementById('password').value = '" + password.getText() + "';";
                String rePwdScript = "javascript:document.getElementById('password-confirm').value = '" + rePassword.getText() + "';";

                if (isWebViewVisible) {
                    webView.setVisibility(View.GONE);
                    isWebViewVisible = true;
                } else {
                    String url = "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/auth?client_id=openremote&redirect_uri=https%3A%2F%2Fuiot.ixxc.dev%2Fmanager%2F&state=92f4b635-32cd-4f10-8a47-82f75868e676&response_mode=fragment&response_type=code&scope=openid&nonce=2e3376d6-caf0-4211-b7d3-bbffb70ed2b8";
                    webView.loadUrl(url);
                    webView.setVisibility(View.VISIBLE);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setDomStorageEnabled(true);
                    webView.setWebViewClient(new WebViewClient(){

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            view.evaluateJavascript("document.querySelector('a').getAttribute('href')", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                    String linkURL = value.replaceAll("^\"|\"$", ""); // Loại bỏ dấu nháy kép nếu có
                                    view.loadUrl("https://uiot.ixxc.dev" + linkURL);
                                }
                            });

                            view.evaluateJavascript(usrScript, null);
                            view.evaluateJavascript(emailScript, null);
                            view.evaluateJavascript(pwdScript, null);
                            view.evaluateJavascript(rePwdScript, null);

                            //view.evaluateJavascript("document.getElementById('kc-register-form').submit();", null);


                            //Intent intent = new Intent(Screen2.this, Screen3.class);
                            //startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}