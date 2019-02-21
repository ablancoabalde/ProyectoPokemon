package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView;
// Para importar la librería de la database
import com.google.firebase.database.*
// Importar las funciones de los LOG
import android.util.Log
import android.view.View
import android.widget.Button
// Importar la función de llamar a los elementos del xml
import kotlinx.android.synthetic.main.activity_main.*

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.firebase.ui.database.FirebaseRecyclerAdapter;






class MainActivity : AppCompatActivity() {
    // para filtrar los logs


    // // referencia a la base de datos del proyecto en firebase
    private var dbCielo: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

    private var eventListener: ValueEventListener? = null



    private val lstPredicciones: RecyclerView? = null

    var mAdapter: FirebaseRecyclerAdapter<*, *>? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_entrar.setOnClickListener{
            val interfaz = Intent(this, Interfaz::class.java)
            Variables.cajaUsuario=txtUsuario.toString();
            startActivity(interfaz)
            finish()
        }








        // Primer punto
        /*
         dbCielo = FirebaseDatabase.getInstance().getReference("predicciones");



        eventListener = (object : ValueEventListener {

            /**
             * Función que llamará automáticamente cada vez que se actualice
             * la información del nodo actual o se produzca cualquier cambio en cualquiera de sus nodos descendientes
             */

            override fun onDataChange(dataSnapshot: DataSnapshot) {
               // val valor = dataSnapshot.value
               // lblCielo.text = valor.toString()

                lblUsuario.setText(dataSnapshot.key.toString());
                lblCielo.setText(dataSnapshot.child("cielo").getValue().toString());
                lblTemperatura.setText(dataSnapshot.child("temperatura").getValue().toString());
                lblHumedad.setText(dataSnapshot.child("humedad").getValue().toString());
            }

            /**
             * Función que llamará cuando la lectura de los datos sea cancelada por cualquier motivo,
             * por ejemplo porque el usuario no tiene permiso para acceder a los datos.
             */
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Error!", databaseError.toException())
            }
        })
        dbCielo!!.addValueEventListener(eventListener as ValueEventListener);

        // Otro ejemplo con días de las semana
        val dbDiasSemana = FirebaseDatabase.getInstance().reference
            .child("dias-semana")

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded: {" + dataSnapshot.key + ": " + dataSnapshot.value + "}")
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: {" + dataSnapshot.key + ": " + dataSnapshot.value + "}")
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved: {" + dataSnapshot.key + ": " + dataSnapshot.value + "}")
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildMoved: " + dataSnapshot.key!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Error!", databaseError.toException())
            }
        }

        dbDiasSemana.addChildEventListener(childEventListener)
        */



    }


}


