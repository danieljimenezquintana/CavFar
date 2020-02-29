package com.example.cavfar.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cavfar.MainActivity;
import com.example.cavfar.R;
import com.example.cavfar.Users.MyAdapter;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    String[] array;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        /*--------------------- RECYCLER VIEW ---------------------*/

        lista = new ArrayList<String>()
        {{
            add("BMW" ) ;
            add("Citroen") ;

        }} ;

        recyclerView = (RecyclerView) findViewById(R.id.contenedorModelos);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //array = Favorites.this.getResources().getStringArray(R.array.Models);
        mAdapter = new MyAdapter(lista, this);
        recyclerView.setAdapter(mAdapter);
    }
}
