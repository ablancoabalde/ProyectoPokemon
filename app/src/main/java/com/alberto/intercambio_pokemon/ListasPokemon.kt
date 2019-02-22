package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_listas_pokemon.*

class ListasPokemon : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listas_pokemon)

        var listV_pokemon = lv_datos

        btn_return.setOnClickListener {
            val lPokemon = Intent(this, Interfaz::class.java)
            startActivity(lPokemon)

        }

    }
}
