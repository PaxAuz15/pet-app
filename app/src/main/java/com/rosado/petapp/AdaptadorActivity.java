package com.rosado.petapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdaptadorActivity extends RecyclerView.Adapter<AdaptadorActivity.ItemViewHolder> {

    Context context;
    ArrayList<Cita> CitaArrayList;
    Locale id = new Locale("es","EC");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-YYYY",id);

    public AdaptadorActivity(Context context, ArrayList<Cita> CitaArrayList) {
        this.context = context;
        this.CitaArrayList = CitaArrayList;
    }

    @NonNull
    @Override
    public AdaptadorActivity.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adaptador, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorActivity.ItemViewHolder holder, int position) {
        System.out.println(position);
        holder.viewBind(CitaArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return CitaArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tipoMascota,
                tv_adaptadorDetalle,
                tv_total,
                tv_fecha;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tipoMascota = itemView.findViewById(R.id.tv_totalShow);
            tv_adaptadorDetalle = itemView.findViewById(R.id.tv_adaptadorDetalle);
            tv_total = itemView.findViewById(R.id.tv_total);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
        }

        public void viewBind(Cita cita) {
            String detalle = null;
            for(String d : cita.getDetalle()){
                if(detalle ==null){
                    detalle = d;
                }else{
                    detalle = detalle +", " + d;
                }
            }
            tv_tipoMascota.setText("Tipo: "+cita.getTipo_mascota());
            tv_adaptadorDetalle.setText("Detalle de la cita: "+detalle);
            tv_total.setText("Total: "+cita.getTotal());
            tv_fecha.setText("Fecha: "+simpleDateFormat.format(cita.getFecha()));
        }
    }
}