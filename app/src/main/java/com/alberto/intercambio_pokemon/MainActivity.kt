package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón para entrar en la app, comprueba que la caja de texto no esté vacía
        btn_entrar.setOnClickListener{

            // Recogemos el valor de la caja de texto en una variable
            var txtNombre =txtUsuario.text.toString().trim()

            // Condición si  la caja de texto está vacía, llama al metodo validation, si no guarda el nombre del usuario
            // variable global y llama a la ventana Interfaz
            if(txtNombre.isEmpty()){
                validation(txtNombre)

            }else{

                Variables.cajaUsuario=txtUsuario.text.toString().trim()
                val interfaz = Intent(this, Interfaz::class.java)
                startActivity(interfaz)
                finish()
            }

        }

    }

    /**
     *  Metodo que muestra un texto de nombre requerido debajo de la caja de texto
     */
    private fun validation(nombre : String ) {

           tilUsuario.setError("Nombre requerido")


    }



}


