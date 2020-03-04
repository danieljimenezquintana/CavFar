package com.example.cavfar.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.example.cavfar.Users.LoginActivity;
import com.example.cavfar.Users.RegisterActivity;
import com.example.cavfar.Users.Usuario;
import com.example.cavfar.Users.loginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    EditText /*modUser, modEmail,*/ modPassword;
    EditText username, email, password;

    TextView modUser;

    Button btnSave;

    String modEmail;

    Usuario user;

    private NotificationManager nm;
    private final String CHANNEL_ID = "Channel2";
    private NotificationCompat.Builder not;
    private final int NOTIFICATION_ID = 666;
    private NotificationChannel ch;
    private final String CHANNEL_NAME = "Notificaciones";

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

                nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                createNotificationChannel();

                not = new NotificationCompat.Builder(Profile.this, CHANNEL_ID);
                not.setSmallIcon(R.drawable.ic_icon); // Android me obliga a definir un icono
                not.setContentTitle("Perfil actualizado.");
                not.setContentText("Su contraseña ha sido actualizada.");
                not.setPriority(NotificationCompat.PRIORITY_HIGH);

                nm.notify(NOTIFICATION_ID, not.build());

            }
        });
    }
    private void createNotificationChannel ()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            ch = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH );
            ch.setDescription(CHANNEL_NAME);

            ch.enableVibration(true);
            ch.setVibrationPattern(new long [] {10000l} );

            ch.enableLights(true);

            nm.createNotificationChannel(ch);

            ch.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
    }

    public void SaveChanges(String modUser, String modEmail, String modPassword){

        Call<loginResponse> call = RetrofitClient.getInstance().getApi().editUser("Bearer " + SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken(),modUser,modEmail,modPassword);

        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                if (response.body() != null) {
                    SharedPrefManager.getInstance(Profile.this).saveUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Toast.makeText(Profile.this,"Hay un problema con el servidor.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
