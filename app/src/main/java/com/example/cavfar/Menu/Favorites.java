package com.example.cavfar.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cavfar.FuenteModelos;
import com.example.cavfar.Interfaz.RetrofitClient;
import com.example.cavfar.Interfaz.SharedPrefManager;
import com.example.cavfar.MainActivity;
import com.example.cavfar.R;
import com.example.cavfar.Users.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorites extends AppCompatActivity {

    String[] array;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<FuenteModelos> lista;

    @Override
    protected void onStart() {
        super.onStart();
        getFavorites();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        /*--------------------- RECYCLER VIEW ---------------------*/

        lista = new ArrayList<FuenteModelos>();

        recyclerView = (RecyclerView) findViewById(R.id.contenedorModelos);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //array = Favorites.this.getResources().getStringArray(R.array.Models);
        mAdapter = new MyAdapter(lista, this);
        recyclerView.setAdapter(mAdapter);
    }
    public void getFavorites(){
        Call<List<FuenteModelos>> call = RetrofitClient.getInstance()
                .getApi()
                .getFavorites("Bearer " + SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken());

        call.enqueue(new Callback<List<FuenteModelos>>() {
            @Override
            public void onResponse(Call<List<FuenteModelos>> call, Response<List<FuenteModelos>> response) {
                lista = response.body();
                mAdapter.setmDataset(lista);
            }

            @Override
            public void onFailure(Call<List<FuenteModelos>> call, Throwable t) {
                Toast.makeText(Favorites.this,"Hay un problema con el servidor.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
