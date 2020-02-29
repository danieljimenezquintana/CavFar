package com.example.cavfar.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cavfar.Interfaz.RetrofitClient;
import com.example.cavfar.Interfaz.SharedPrefManager;
import com.example.cavfar.MainActivity;
import com.example.cavfar.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister ;
    private EditText correo, password ;
    private String email, pass ;

    private TextView inv;

    private final int codLogin= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin) ;
        btnRegister = findViewById(R.id.btnRegister) ;
        correo  = findViewById(R.id.email) ;
        password   = findViewById(R.id.Contrasena) ;
        inv = findViewById(R.id.invitado);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(in,codLogin);
            }
        });

        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                in.putExtra("codMenu",0);
                startActivityForResult(in,codLogin);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("codMenu",1);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    public void userLogin(){
        email = correo.getText().toString().trim();
        pass = password.getText().toString().trim();

        Call<loginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(email,pass);
        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                if(response.body() != null){
                    SharedPrefManager.getInstance(LoginActivity.this).saveUser(response.body());
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("codMenu",1);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this,"Los datos son incorrectos, si no tienes usuario registrate.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Hay un problema con la red.",Toast.LENGTH_LONG).show();

            }
        });
    }
}
