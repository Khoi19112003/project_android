package com.example.project_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_android.api.apiService_token;
import com.example.project_android.model.token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen3 extends AppCompatActivity {

    private int code = 0;
    private token token1;
    public interface ApiCallback {
        void onApiCallComplete(int statusCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageView = findViewById(R.id.image1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen3.this, Screen3_vn.class);
                startActivity(intent);
            }
        });

        EditText editText_password = findViewById(R.id.password);
        editText_password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);
                String user = String.valueOf(username.getText());
                String pass = String.valueOf(password.getText());
                callAPIGetToken(user, pass, new ApiCallback() {
                    @Override
                    public void onApiCallComplete(int statusCode) {
                        if (statusCode == 200) {
                            Intent intent = new Intent(Screen3.this, Screen4.class);
                            startActivity(intent);
                        } else {
                            showAlertDialog();
                        }
                    }
                });
            }
        });
    }




    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SAI")
                .setMessage("Vui lòng nhập lại.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Xử lý khi người dùng nhấn nút OK trong hộp thoại
                        EditText editText_pasword = findViewById(R.id.password);
                        editText_pasword.setText("");
                        EditText editText_usr = findViewById(R.id.username);
                        editText_usr.setText("");
                    }
                })
                .show();
    }

    private void callAPIGetToken(String user, String password, ApiCallback callback){
        apiService_token.api_token.getLoginToken("openremote", user, password, "password", "application/x-www-form-urlencoded").enqueue(new Callback<token>() {
            @Override
            public void onResponse(Call<token> call, Response<token> response) {
                Log.d("API CALL", response.code()+"");
                code = response.code();
                callback.onApiCallComplete(code);
                token1 = response.body();
            }
            @Override
            public void onFailure(Call<token> call, Throwable t) {
                Log.d("API CALL", t.getMessage().toString());
                callback.onApiCallComplete(-1);
            }
        });
    }
}