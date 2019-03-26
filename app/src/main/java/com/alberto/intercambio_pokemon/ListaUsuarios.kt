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

    // Referencia a la base de datos del proyecto en firebase
    private var fireDatabase= FirebaseDatabase.getInstance().getReference()

    // Variables generales
    var lPersona: MutableList<String> = mutableListOf()
    var lKeys: MutableList<String> = mutableListOf()
    // Para el formateo de la tabla de datos del xml
    var arrayadapterPersona : ArrayAdapter<String>? = null
    var listV_personas : ListView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_usuarios)

        // Botón que llama a la ventana Interfaz
        btn_return.setOnClickListener {
            val lPokemon = Intent(this, Interfaz::class.java)
            startActivity(lPokemon)
        }

        // Introducimos el modelo de la lista de datos xml
        listV_personas = lv_datos

        // Llamada al metodo listarDatos
        listarDatos()

        // Función que al pulsar encima del usuario recoge la key(token) para buscar en la base de datos sus pokemons
        // almacenados, almacena esta información en una variable global que luego se usará en la consulta a la base de datos
        // de la Ventana ListaPokemons
        listV_personas?.setOnItemClickListener { parent, view, position, id ->
            Variables.personaSeleccionada=lKeys.get(position) as String
            val lPokemon = Intent(this, ListaPokemons::class.java)
            startActivity(lPokemon)

        }

    }

    /**
     * Metodo que lista todos los usuarios de la base de datos
     */
    private fun listarDatos() {

        // Cursor de la base de datos
        fireDatabase.child("Usuario").addValueEventListener(object : ValueEventListener {

            /**
             * Metodo cada vez que se realice un cambio en la base de datos esta se actualiza
             */
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // Borramos la lista de Personas para que tras cada consulta no se dupliquen
                lPersona.clear()

                // Hacemos un cursor en la base de datos para recoger el nombre y las keys de todos los usuarios
                for (snapshot in dataSnapshot.children) {
                    // Creamos un objeto persona
                    var p = Persona()
                    //  Metemos el resultado de la consulta
                    p= snapshot.getValue(Persona::class.java)!!
                    // Agregamos a la MutableList los nombres de la base de datos
                    lPersona.add(p.nombre)
                    // Agregamos a la MutableList las keys de la base de datos
                    lKeys.add(snapshot.key.toString())
                    // Modelo para adaptar la consulta a la lista xml
                    arrayadapterPersona=ArrayAdapter<String>(this@ListaUsuarios,android.R.layout.simple_list_item_1,lPersona)
                    // Agregamos el modelo a la lista
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
