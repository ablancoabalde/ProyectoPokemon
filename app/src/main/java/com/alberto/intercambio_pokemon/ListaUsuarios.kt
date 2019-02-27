package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_lista_usuarios.*

class ListaUsuarios : AppCompatActivity() {
    val TAG = "AppCompatActivity"

    // // referencia a la base de datos del proyecto en firebase
    private var fireDatabase= FirebaseDatabase.getInstance().getReference()

    var arrayadapterPersona : ArrayAdapter<String>? = null

    var listV_personas : ListView? = null

    var lPersona: MutableList<String> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_usuarios)

        btn_return.setOnClickListener {
            val lPokemon = Intent(this, Interfaz::class.java)
            startActivity(lPokemon)
        }

        listV_personas = lv_datos

        listarDatos()

        listV_personas?.setOnItemClickListener { parent, view, position, id ->
           Variables.personaSeleccionada=parent.getItemAtPosition(position) as String
            val lPokemon = Intent(this, ListaPokemons::class.java)
            startActivity(lPokemon)

        }

    }

    private fun listarDatos() {

        fireDatabase.child("Usuario").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lPersona.clear()

                for (snapshot in dataSnapshot.children) {
                    var p = Persona()
                    p= snapshot.getValue(Persona::class.java)!!
                    lPersona.add(p.nombre)


                    arrayadapterPersona=ArrayAdapter<String>(this@ListaUsuarios,android.R.layout.simple_list_item_1,lPersona)
                    listV_personas!!.adapter=arrayadapterPersona
                }

            }

            /**
             * Función que llamará cuando la lectura de los datos sea cancelada por cualquier motivo,
             * por ejemplo porque el usuario no tiene permiso para acceder a los datos.
             */
            override fun onCancelled(@NonNull databaseError: DatabaseError) {

            }
        })
    }
}
