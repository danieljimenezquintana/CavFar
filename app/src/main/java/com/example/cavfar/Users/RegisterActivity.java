package com.example.cavfar.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cavfar.Interfaz.RetrofitClient;
import com.example.cavfar.Interfaz.SharedPrefManager;
import com.example.cavfar.MainActivity;
import com.example.cavfar.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText usuario , nombre, correo, password2, password_Confirm2;
    private String user , name, email, password, password_Confirm;

    private Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnregister = findViewById(R.id.btnRegist);

        usuario = findViewById(R.id.regUsuario);
        user = usuario.getText().toString().trim();

        nombre = findViewById(R.id.regName);
        name = nombre.getText().toString().trim();

        correo = findViewById(R.id.regEmail);
        email = correo.getText().toString().trim();

        password2 = findViewById(R.id.regPassword);
        password = password2.getText().toString().trim();

        password_Confirm2 = findViewById(R.id.regCpassword);
        password_Confirm = password_Confirm2.getText().toString().trim();


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    public void register(){
        name = nombre.getText().toString().trim();
        user = usuario.getText().toString().trim();
        email = correo.getText().toString().trim();
        password = password2.getText().toString().trim();
        password_Confirm = password_Confirm2.getText().toString().trim();

        Call<loginResponse> call = RetrofitClient.getInstance().getApi().createUser(name,user,email,password,password_Confirm);

        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                if (response.body() != null) {
                    SharedPrefManager.getInstance(RegisterActivity.this).saveUser(response.body());
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    i.putExtra("codMenu",1);
                    startActivity(i);
                } else {
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Hay un problema con los datos.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
