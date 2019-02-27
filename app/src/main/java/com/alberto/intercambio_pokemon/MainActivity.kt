package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_entrar.setOnClickListener{
            var MNombre =txtUsuario.text.toString().trim()
            if(MNombre.isEmpty()){
                validation(MNombre)

            }else{
                val interfaz = Intent(this, Interfaz::class.java)
                Variables.cajaUsuario=txtUsuario.text.toString().trim()
                startActivity(interfaz)
                finish()
            }

        }

    }


    private fun validation(Nombre : String ) {

       if (Nombre.isEmpty()){
           tilUsuario.setError("Required")
       }
    }


}


