package com.example.cavfar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cavfar.Interfaz.RetrofitClient;
import com.example.cavfar.Interfaz.SharedPrefManager;
import com.example.cavfar.Menu.Favorites;
import com.example.cavfar.Menu.Profile;
import com.example.cavfar.Users.LoginActivity;
import com.example.cavfar.Users.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final int codLogin= 1;

    String[] options;
    ImageView favorite;
    //String[] array;

    private List<FuenteModelos> lista ;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onStart() {
        super.onStart();
        showAllModels();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAllModels();
        /*--------------------- SPINNER ---------------------*/
        Spinner spinner = (Spinner) findViewById(R.id.spinnerMarcas);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Cars, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        options = MainActivity.this.getResources().getStringArray(R.array.Cars);

        /*--------------------- RECYCLER VIEW ---------------------*/
        lista = new ArrayList();
        recyclerView = (RecyclerView) findViewById(R.id.contenedorModelos);

        //recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        //array = MainActivity.this.getResources().getStringArray(R.array.Models);
        mAdapter = new MyAdapter(lista,getApplicationContext());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(this, " You select --> " + options[position], Toast.LENGTH_SHORT).show();
        if (options[position] == "BMW"){
            showModel(1);
        }
        else if(options[position] == "Audi"){
            showModel(2);
        }else {
            showModel(3);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle bundle = getIntent().getExtras();
        if ((bundle.getInt("codMenu")) == 1){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.register, menu);
            return true;
        } else{
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.profile2, menu);
            return true;
        }
    }

    private void logOut(){
        SharedPrefManager.getInstance(getApplicationContext()).clear();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent= new Intent (MainActivity.this, Profile.class);
                startActivity(intent);
                break;
            case R.id.favoritos:
                Intent intent2= new Intent (MainActivity.this, Favorites.class);
                startActivity(intent2);
                break;
            case R.id.cerrarSesion:
                Toast.makeText(this,"Ha cerrado sesión",Toast.LENGTH_SHORT);
                Intent intent3= new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intent3);
                logOut();
                setResult(0) ;
                finish() ;
                break;
            case R.id.registroInvi:
                Intent intent4= new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intent4);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showAllModels(){
        Call<List<FuenteModelos>> call = RetrofitClient.getInstance().getApi().showAllModels("Bearer " + SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken());

        call.enqueue(new Callback<List<FuenteModelos>>() {
            @Override
            public void onResponse(Call<List<FuenteModelos>> call, Response<List<FuenteModelos>> response) {
                lista = response.body();
                mAdapter.setmDataset(lista);
                Log.i("Error",response.body().toString());
            }
            @Override
            public void onFailure(Call<List<FuenteModelos>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Hay un problema con el servidor.",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void showModel(Integer id){
        Call<List<FuenteModelos>> call = RetrofitClient.getInstance().getApi().showModels("Bearer " + SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken(),id);

        call.enqueue(new Callback<List<FuenteModelos>>() {
            @Override
            public void onResponse(Call<List<FuenteModelos>> call, Response<List<FuenteModelos>> response) {
                if (response.body() != null) {
                    lista = response.body();
                    mAdapter.setmDataset(lista);
                    Log.i("Error",response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<List<FuenteModelos>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error al conectar con el servidor.",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void addFav(String id){
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addFav("Bearer " + SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken(),id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    Log.i("Error","Ha sido añadido a favoritos correctamente");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Error","No se ha podido agregar a favoritos.");
            }
        });
    }
}
