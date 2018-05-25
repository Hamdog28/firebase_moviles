package com.example.mariapizarro.tarea6;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaProductos extends AppCompatActivity {

    ListView lv;
    public final ArrayList<Producto> listaProductos = new ArrayList<>();
    int cont = 0;
    AdapterProducto adapter;
    DatabaseReference databaseProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        lv = findViewById(R.id.listView);

        databaseProductos = FirebaseDatabase.getInstance().getReference("productos");

        //populateLv(this);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int idProducto = position;
                new android.support.v7.app.AlertDialog.Builder(ListaProductos.this)
                        .setTitle("Alerta")
                        .setMessage("Desea eliminar el producto?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id = listaProductos.get(idProducto).getId();FirebaseDatabase database = FirebaseDatabase.getInstance();
                                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database1.getReference("productos");
                                myRef.child(id).removeValue();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return false;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        /*databaseProductos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaProductos.clear();
                for (DataSnapshot productoSnapshot : dataSnapshot.getChildren()){
                    String nombre = productoSnapshot.child("nombre").getValue().toString();
                    String precio = productoSnapshot.child("precio").getValue().toString();
                    String descripcion = productoSnapshot.child("descripcion").getValue().toString();

                    listaProductos.add(new Producto(dataSnapshot.getKey(),nombre,Integer.valueOf(precio),descripcion));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        Log.e("adf",String.valueOf(MainActivity.listaProductos.size()));
        adapter = new AdapterProducto(MainActivity.listaProductos,this);
        lv.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //adapter.stopListening();
    }

    public void logOut(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ListaProductos.this, MainActivity.class));
        finish();
    }

    public void agregar_productoActivity(View view){
        startActivity(new Intent(ListaProductos.this, addProduct.class));
        finish();
    }

    public void populateLv(Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("productos");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    Log.e("asdf",ds.child("precio").getValue().toString());

                    String nombre = ds.child("nombre").getValue().toString();
                    String precio = ds.child("precio").getValue().toString();
                    String descripcion = ds.child("descripcion").getValue().toString();

                    listaProductos.add(new Producto(ds.getKey(),nombre,Integer.valueOf(precio),descripcion));

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new AdapterProducto(listaProductos,this);
        lv.setAdapter(adapter);

    }
}
