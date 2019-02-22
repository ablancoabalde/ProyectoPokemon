package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_listas_pokemon.*

class ListasPokemon : AppCompatActivity() {

    // // referencia a la base de datos del proyecto en firebase
    private var fireDatabase= FirebaseDatabase.getInstance().getReference()

    private var eventListener: ValueEventListener? = null

    var listV_pokemon: ListView? = null

    var LPersona: MutableList<Persona> = mutableListOf()
  //  var arrayAdpterPersona: ArrayAdapter<Persona> = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listas_pokemon)


        listV_pokemon = findViewById<ListView>(R.id.lv_datos)


        btn_return.setOnClickListener {
            val lPokemon = Intent(this, Interfaz::class.java)
            startActivity(lPokemon)

            listarDatos()

        }

    }

    private fun listarDatos() {

        fireDatabase = FirebaseDatabase.getInstance().getReference("predicciones");



        eventListener = (object : ValueEventListener {

            /**
             * Función que llamará automáticamente cada vez que se actualice
             * la información del nodo actual o se produzca cualquier cambio en cualquiera de sus nodos descendientes
             */

            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                LPersona.clear()

                for (obj in dataSnapshot.children){
                    var p = Persona()
                     p = obj.value as Persona
                    LPersona.add(p)

                   // arrayAdpterPersona= ArrayAdapter<Persona>(this,R.layout.lv_datos,LPersona)

                }


            }

            /**
             * Función que llamará cuando la lectura de los datos sea cancelada por cualquier motivo,
             * por ejemplo porque el usuario no tiene permiso para acceder a los datos.
             */
            override fun onCancelled(@NonNull databaseError: DatabaseError) {

            }
        })
        fireDatabase!!.addValueEventListener(eventListener as ValueEventListener);


    }
}


