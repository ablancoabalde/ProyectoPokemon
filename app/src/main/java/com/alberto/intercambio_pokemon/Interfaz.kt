package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_interfaz.*

class Interfaz : AppCompatActivity() {

    val TAG = "AppCompatActivity"

    var Inombre= Variables.cajaUsuario
    var INomPokemon = txtPokemon.toString()
    var ICantidad = txtCantidad.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz)

        Toast.makeText(this, Inombre, Toast.LENGTH_SHORT).show()

        btn_ver.setOnClickListener{
            val lPokemon = Intent(this, ListasPokemon::class.java)
            startActivity(lPokemon)
        }

    }


}
