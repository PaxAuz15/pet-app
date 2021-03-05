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
    List<Usuario> listaUsuarios;

    public AdaptadorActivity(Context context, List<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adaptador, null, false);
        return new UsuarioViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        holder.tvUsuario.setText(listaUsuarios.get(position).getUsuario());
        holder.tvContrasena.setText(listaUsuarios.get(position).getContrasena());
        holder.tvTelefono.setText(listaUsuarios.get(position).getTelefono());
        holder.tvEmail.setText(listaUsuarios.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
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