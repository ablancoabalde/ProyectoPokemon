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

    // Referencia a la base de datos del proyecto en firebase
    private var fireDatabase= FirebaseDatabase.getInstance().getReference("Usuario")

    // Variables generales
    var lPokemons: MutableList<Pokemons> = mutableListOf()

    // Para el formateo de la tabla de datos del xml
    var arrayadapterPokemons : ArrayAdapter<Pokemons>? = null
    var listV_pokemon : ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pokemons)

        // Botón que llama a la ventana ListaUsuarios
        btn_return.setOnClickListener {
            val lPokemon = Intent(this, ListaUsuarios::class.java)
            startActivity(lPokemon)
        }

        // Introducimos el modelo de la lista de datos xml
        listV_pokemon = lv_datos

        // Llamada al metodo listarDatos
        listarDatos()

    }

    /**
     * Metodo que lista los datos de los pokemons del usuario seleccionado
     */
    private fun listarDatos() {

        // Cursor de la base de datos
        fireDatabase.child(Variables.personaSeleccionada).addValueEventListener(object : ValueEventListener {

            /**
             * Metodo cada vez que se realice un cambio en la base de datos esta se actualiza
             */
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Borramos la lista de Pokemons para que tras cada consulta no se dupliquen
                lPokemons.clear()

                // Hacemos un cursor en la base de datos para recoger el arraylist que contiene todos los pokemons
                // con sus cantidades
                for (snapshot in dataSnapshot.child("arrayList").children) {
                    // Creamos un objeto pokemon
                    var p = Pokemons()
                    //  Metemos el resultado de la consulta en una variable pokemon de la clase Pokemon
                    p= snapshot.getValue(Pokemons::class.java)!!
                    // Agregamos a la MutableList la consulta pokemon
                    lPokemons.add(p)
                    // Modelo para adaptar la consulta a la lista xml
                    arrayadapterPokemons=ArrayAdapter<Pokemons>(this@ListaPokemons,android.R.layout.simple_list_item_1,lPokemons)
                    // Agregamos el modelo a la lista
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
