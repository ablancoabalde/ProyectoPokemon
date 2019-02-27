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

    var arrayadapterPersona : ArrayAdapter<Persona>? = null

    var listV_pokemon : ListView? = null

    var lPersona: MutableList<Persona> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listas_pokemon)

        btn_return.setOnClickListener {
            val lPokemon = Intent(this, Interfaz::class.java)
            startActivity(lPokemon)
        }

        listV_pokemon = lv_datos

        listarDatos()

    }

    private fun listarDatos() {

        fireDatabase.child("Usuario").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lPersona.clear()

                for (snapshot in dataSnapshot.children) {
                    var p = Persona()
                    p= snapshot.getValue(Persona::class.java)!!
                    lPersona.add(p)


                    arrayadapterPersona=ArrayAdapter<Persona>(this@ListasPokemon,android.R.layout.simple_list_item_1,lPersona)
                    listV_pokemon!!.adapter=arrayadapterPersona
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


