package com.example.cavfar.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import com.example.cavfar.Users.RegisterActivity;
import com.example.cavfar.Users.Usuario;
import com.example.cavfar.Users.loginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    EditText modUser/*, modEmail*/, modPassword;
    EditText username, email, password;

    Button btnSave;

    String modEmail;

    Usuario user;

    NotificationManager nm;
    NotificationCompat.Builder not;
    NotificationChannel ch;
    final String CHANNEL_ID = "Channel2";
    final String CHANNEL_NAME = "CanalCavFar";
    //final int NOTIFICATION_ID = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        modUser = findViewById(R.id.editUser);
        /*modEmail = (EditText) findViewById(R.id.editEmail);*/
        modEmail = "";
        modPassword = (EditText) findViewById(R.id.editPassword);

        btnSave = findViewById(R.id.btnSaveChange);

        user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        modUser.setText(user.getNombre());
        /*modEmail.setText(user.getEmail());*/


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveChanges(modUser.getText().toString().trim()/*, modEmail.getText().toString().trim()*/,modEmail, modPassword.getText().toString().trim());
            }
        });
    }
    public void SaveChanges(String modUser, String modEmail, String modPassword){

        Call<loginResponse> call = RetrofitClient.getInstance().getApi().editUser("Bearer " + SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken(),modUser,modEmail,modPassword);

        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                Toast.makeText(Profile.this,response.toString(),Toast.LENGTH_LONG).show();
                if (response.body() != null) {
                    SharedPrefManager.getInstance(Profile.this).saveUser(response.body());
                    Toast.makeText(Profile.this,"Okey",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Toast.makeText(Profile.this,"Hay un problema con el servidor.",Toast.LENGTH_LONG).show();
            }
        });
        //--------------------- Notificación ---------------------
       /* nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       not = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_forocars)
                .setContentTitle("CavFar")
                .setContentText("Has modificado los datos del usuario.");
        ch = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

        ch.enableVibration(true);
        ch.setVibrationPattern(new long[]{ 100001 });
        ch.enableLights(true);

        ch.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        nm.notify(NOTIFICATION_ID, not.build());*/
    }
}
