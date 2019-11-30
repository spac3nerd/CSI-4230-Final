package com.example.csi_5230_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csi_5230_final.DTO.LoginDTO;
import com.example.csi_5230_final.DTO.LoginResponseDTO;
import com.example.csi_5230_final.constants.Constants;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String baseURL = "http://10.0.2.2:8080/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();

    OkHttpClient http;
    private Handler toastHandler;

    Button loginBtn;
    EditText loginUserName;
    EditText loginPassword;
    String authToken;

    private void invalidEmailMessage() {
        Toast.makeText(MainActivity.this,"Invalid Email!",Toast.LENGTH_SHORT).show();
    }
    private void invalidPasswordMessage() {
        Toast.makeText(MainActivity.this,"Invalid Password!",Toast.LENGTH_SHORT).show();
    }

    private void somethingWentWrongMessage() {
        Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
    }

    private void invalidLoginMessage() {
        Toast.makeText(MainActivity.this,"Email or password are incorrect!",Toast.LENGTH_SHORT).show();
    }

    private void setupToastHandler() {
        toastHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.INVALID_USERNAME:
                        invalidEmailMessage();
                        break;
                    case Constants.INVALID_PASSWORD:
                        invalidPasswordMessage();
                        break;
                    case Constants.LOGIN_FAIL:
                        invalidLoginMessage();
                        break;
                    case Constants.GENERIC_FAIL:
                        somethingWentWrongMessage();
                        break;
                    default:
                        break;
                }
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        http = new OkHttpClient();


        //Login screen controls
        loginBtn = findViewById(R.id.loginButton);
        loginUserName = findViewById(R.id.userName);
        loginPassword = findViewById(R.id.password);

        //set up Toast handler
        setupToastHandler();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("click");
                String userName = loginUserName.getText().toString();
                String userPass = loginPassword.getText().toString();

                System.out.println(userName);
                System.out.println(userPass);

                if (userName.length() == 0) {
                    toastHandler.sendEmptyMessage(Constants.INVALID_USERNAME);
                    return;
                }

                if (userPass.length() == 0) {
                    toastHandler.sendEmptyMessage(Constants.INVALID_PASSWORD);
                    return;
                }

                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setEmail(userName);
                loginDTO.setPassword(userPass);
                login(loginDTO);

            }
        });
    }

    private void login(LoginDTO loginDTO) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(loginDTO));
        Request loginRequest = new Request.Builder().url(baseURL + "user/login").post(body).build();


        http.newCall(loginRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                somethingWentWrongMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response);
                LoginResponseDTO responseDTO = gson.fromJson(response.body().string(), LoginResponseDTO.class);

                //check if successful
                if (responseDTO.getSuccess()) {
                    authToken = responseDTO.getAuthtoken();
                }
                else {
                    toastHandler.sendEmptyMessage(Constants.LOGIN_FAIL);
                }
            }
        });
    }
}
