package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_lista_usuarios.*

class ListaPokemons : AppCompatActivity() {

    // referencia a la base de datos del proyecto en firebase
    private var fireDatabase= FirebaseDatabase.getInstance().getReference("Usuario")

    var arrayadapterPokemons : ArrayAdapter<Pokemons>? = null

    var listV_pokemon : ListView? = null

    var lPokemons: MutableList<Pokemons> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pokemons)

        btn_return.setOnClickListener {
            val lPokemon = Intent(this, ListaUsuarios::class.java)
            startActivity(lPokemon)
        }

        listV_pokemon = lv_datos

        listarDatos()

    }
    private fun listarDatos() {

        fireDatabase.child(Variables.personaSeleccionada).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lPokemons.clear()

                for (snapshot in dataSnapshot.child("arrayList").children) {
                    var p = Pokemons()
                    p= snapshot.getValue(Pokemons::class.java)!!
                    lPokemons.add(p)


                    arrayadapterPokemons=ArrayAdapter<Pokemons>(this@ListaPokemons,android.R.layout.simple_list_item_1,lPokemons)
                    listV_pokemon!!.adapter=arrayadapterPokemons
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
