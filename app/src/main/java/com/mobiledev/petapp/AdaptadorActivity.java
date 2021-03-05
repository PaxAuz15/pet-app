package com.mobiledev.petapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorActivity extends RecyclerView.Adapter<AdaptadorActivity.UsuarioViewHolder> {

    Context context;
    List<Citas> listaCitas;

    public AdaptadorActivity(Context context, List<Citas> listaCitas) {
        this.context = context;
        this.listaCitas = listaCitas;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adaptador, null, false);
        return new UsuarioViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        holder.tvUsuario.setText(listaCitas.get(position).getTipo_mascota());
        holder.tvContrasena.setText(listaCitas.get(position).getContrasena());
        holder.tvTelefono.setText(listaCitas.get(position).getTelefono());
        holder.tvEmail.setText(listaCitas.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return listaCitas.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsuario, tvContrasena, tvTelefono, tvEmail;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsuario = itemView.findViewById(R.id.tvUsuario);
            tvContrasena = itemView.findViewById(R.id.tvContrasena);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}