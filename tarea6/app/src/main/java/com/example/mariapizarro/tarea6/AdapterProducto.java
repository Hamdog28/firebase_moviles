package com.example.mariapizarro.tarea6;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anthony-PC on 24/5/2018.
 */

public class AdapterProducto extends BaseAdapter {

    private ArrayList<Producto> lista;
    private Context context;
    private LayoutInflater inflater;



    public AdapterProducto(ArrayList<Producto> lista,Context context) {
        this.lista = lista;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }



    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_item,null,true);
        ImageView iv_imagen =  v.findViewById(R.id.imageView);
        TextView nombre = (TextView) v.findViewById(R.id.nombre);
        TextView precio = (TextView) v.findViewById(R.id.precio);
        TextView descripcion =  (TextView) v.findViewById(R.id.descripcion);
        DownloadTask downloadTask = new DownloadTask();
        try {
            Bitmap result = downloadTask.execute("https://firebasestorage.googleapis.com/v0/b/tarea6-d34b6.appspot.com/o/"+
                    lista.get(i).getNombre()+"?alt=media&token=eb71b096-c31e-4b8c-95f9-447b6d3fb8ac)").get();
            iv_imagen.setImageBitmap(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        nombre.setText("   "+lista.get(i).getNombre());
        precio.setText("   "+String.valueOf(lista.get(i).getPrecio()));
        descripcion.setText("   "+lista.get(i).getDescripcion());
        return v;
    }
}
