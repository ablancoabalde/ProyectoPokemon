package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_interfaz.*
import java.util.*


class Interfaz : AppCompatActivity() {

    val TAG = "AppCompatActivity"

    // // referencia a la base de datos del proyecto en firebase
    private var fireDatabase= FirebaseDatabase.getInstance().getReference()

    var Inombre: String = ""
    var INomPokemon: String = ""
    var ICantidad: String = ""

    var IarrayList: MutableList<Pokemons> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz)

        Inombre= Variables.cajaUsuario




        btn_ver.setOnClickListener{
            val lPokemon = Intent(this, ListasPokemon::class.java)
            startActivity(lPokemon)
        }

        btn_mas.setOnClickListener {
            INomPokemon = txtPokemon.text.toString().trim()
            ICantidad = txtCantidad.text.toString().trim()
            if(INomPokemon.isEmpty()||ICantidad.isEmpty()){
                validation(INomPokemon,ICantidad)
            }else{
                val pk = Pokemons()
                pk.nombrePokemon=INomPokemon
                pk.cantidad=ICantidad
                IarrayList.add(pk)
                limpiarCajas()
            }
        }

        btn_enviar.setOnClickListener {
            INomPokemon = txtPokemon.text.toString().trim()
            ICantidad = txtCantidad.text.toString().trim()

            if(IarrayList.isEmpty()){

                if(INomPokemon.isEmpty()||ICantidad.isEmpty()){
                    validation(INomPokemon,ICantidad)
                }else{
                    val pk = Pokemons()
                    pk.nombrePokemon=INomPokemon
                    pk.cantidad=ICantidad
                    IarrayList.add(pk)

                    val p = Persona()
                    p.id=UUID.randomUUID().toString()
                    p.nombre=Inombre
                    p.arrayList=IarrayList
                    fireDatabase.child("Usuario").child(p.id).setValue(p)
                    limpiarCajas()
                }
            }else{
                val p = Persona()
                p.id=UUID.randomUUID().toString()
                p.nombre=Inombre
                p.arrayList=IarrayList
                fireDatabase.child("Usuario").child(p.id).setValue(p)
                limpiarCajas()
            }




        }


    }



    private fun limpiarCajas(){
        txtPokemon.text?.clear()
        txtCantidad.text?.clear()
    }

    private fun validation(Nombre : String, Cantidad : String ) {

        if (Nombre.isEmpty()){
            tilNPokemon.setError("Required")
        }else if(Cantidad.isEmpty()){
            tilCantidad.setError("Required")
        }
    }


}

