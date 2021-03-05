package com.mobiledev.petapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AgendaCitasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText etUsuario, etContrasena, etTelefono, etEmail;
    Button btnConsultar, btnConsultarCita, btnAgregar, btnEditar, btnEliminar,btnlogout;;
    RecyclerView rvUsuarios;
    Spinner spinner;
    TextView textView;
    String item;
    DatabaseReference databaseReference;
    List<Citas> listaCitas = new ArrayList<>();
    AdaptadorActivity adaptador;
    String[] tipo_mascotas = {"Elige un tipo de mascota", "Perro", "Gato", "Conejo", "Tortuga"};
    Citas cita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_citas);
        btnlogout = findViewById(R.id.logoutButton);
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultarCita = findViewById(R.id.btnConsultarCita);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));
        databaseReference = FirebaseDatabase.getInstance().getReference();

        textView = findViewById(R.id.tv_mascotas);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        cita = new Citas();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,tipo_mascotas);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AgendaCitasActivity.this, MainActivity.class));

            }
        });


        obtenerUsuarios();

        btnAgregar.setOnClickListener(view -> agregarUsuario());

        btnEditar.setOnClickListener(view -> editarUsuario());

        btnEliminar.setOnClickListener(view -> eliminarUsuario());

        btnConsultar.setOnClickListener(view -> obtenerUsuarios());

        btnConsultarCita.setOnClickListener(view -> obtenerUsuario());
    }

    public void obtenerUsuario() {
        listaCitas.clear();
        String usuario = etUsuario.getText().toString();

        Query query = databaseReference.child("usuario").orderByChild("usuario").equalTo(usuario);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaCitas.add(objeto.getValue(Citas.class));
                }

                adaptador = new AdaptadorActivity(AgendaCitasActivity.this, listaCitas);
                rvUsuarios.setAdapter(adaptador);

                limpiarCampos();
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }

    public void obtenerUsuarios() {
        listaCitas.clear();
        databaseReference.child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaCitas.add(objeto.getValue(Citas.class));
                }

                adaptador = new AdaptadorActivity(AgendaCitasActivity.this, listaCitas);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

    public void agregarUsuario() {
        listaCitas.clear();
        String user=etUsuario.getText().toString();
        String name=etContrasena.getText().toString();
        String tel=etTelefono.getText().toString();
        String emai=etEmail.getText().toString();
        String item=spinner.getSelectedItem().toString();

        if (item == "Elige un tipo de mascota"){
            Toast.makeText(this,"Elegir un tipo de mascota",Toast.LENGTH_SHORT).show();
        }else if(user.isEmpty()||name.isEmpty()||tel.isEmpty()||emai.isEmpty()){
            Toast.makeText(AgendaCitasActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            Citas citas = new Citas(
                    user,name,tel,emai,item
            );

        /*Usuario usuario = new Usuario(
                etUsuario.getText().toString(),
                etContrasena.getText().toString(),
                etTelefono.getText().toString(),
                etEmail.getText().toString()
        );*/

            databaseReference.child("usuario").push().setValue(citas,
                    new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            Toast.makeText(AgendaCitasActivity.this, "Usuario Añadido.", Toast.LENGTH_SHORT).show();
                        }
                    });

            limpiarCampos();
        }

    }



    public void editarUsuario() {
        listaCitas.clear();
        String user=etUsuario.getText().toString();
        String name=etContrasena.getText().toString();
        String tel=etTelefono.getText().toString();
        String emai=etEmail.getText().toString();
        String item=spinner.getSelectedItem().toString();

    if(item == "Elige un tipo de mascota") {
        Toast.makeText(this, "Elegir un tipo de mascota", Toast.LENGTH_SHORT).show();
    }else if(user.isEmpty()||name.isEmpty()||tel.isEmpty()||emai.isEmpty()){
            Toast.makeText(AgendaCitasActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            Citas citas = new Citas(
                    user,name,tel,emai,item
            );
            Query query = databaseReference.child("usuario").orderByChild("usuario").equalTo(citas.getTipo_mascota());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String key ="";
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        key = ds.getKey(); // Obtiene el id del registro para poderlo editar
                    }
                    if(key.isEmpty()){
                        Toast.makeText(AgendaCitasActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }else{
                        databaseReference.child("usuario").child(key).setValue(citas);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            limpiarCampos();
        }



    }

    public void eliminarUsuario() {
        listaCitas.clear();
        String usuario = etUsuario.getText().toString();

        if(usuario.isEmpty()){
            Toast.makeText(AgendaCitasActivity.this, "Ingrese usuario correcto", Toast.LENGTH_SHORT).show();
        }else{
            Query query = databaseReference.child("usuario").orderByChild("usuario").equalTo(usuario);
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                        objeto.getRef().removeValue();
                    }
                    Toast.makeText(AgendaCitasActivity.this, "Se elimino el usuario", Toast.LENGTH_SHORT).show();
                }


                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            limpiarCampos();
        }


    }

    public void limpiarCampos() {
        etUsuario.setText("");
        etContrasena.setText("");
        etTelefono.setText("");
        etEmail.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}