package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_interfaz.*


class Interfaz : AppCompatActivity() {

    val TAG = "AppCompatActivity"

    // // referencia a la base de datos del proyecto en firebase
    private var fireDatabase= FirebaseDatabase.getInstance().getReference()

    var iNnombre: String = ""
    var iNomPokemon: String = ""
    var iCantidad: String = ""

    var iArrayList: MutableList<Pokemons> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz)

        iNnombre= Variables.cajaUsuario

        btn_ver.setOnClickListener{
            val lPokemon = Intent(this, ListaUsuarios::class.java)
            startActivity(lPokemon)
        }

        initListener()
    }

    private fun initListener() {
        val childEventListener = object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                iArrayList.clear()
                for (snapshot in dataSnapshot.child(iNnombre).child("arrayList").children) {

                    var p:Pokemons= snapshot.getValue(Pokemons::class.java)!!
                    val pk = Pokemons()
                    pk.nombrePokemon=p.nombrePokemon
                    pk.cantidad=p.cantidad
                    iArrayList.add(p)
                }

                btn_mas.setOnClickListener {
                    iNomPokemon = txtPokemon.text.toString().trim()
                    iCantidad = txtCantidad.text.toString().trim()
                    if(iNomPokemon.isEmpty()||iCantidad.isEmpty()){
                        validation(iNomPokemon,iCantidad)
                    }else{
                        val pk = Pokemons()
                        pk.nombrePokemon=iNomPokemon
                        pk.cantidad=iCantidad
                        iArrayList.add(pk)
                        limpiarCajas()
                    }
                }

                btn_enviar.setOnClickListener {
                    iNomPokemon = txtPokemon.text.toString().trim()
                    iCantidad = txtCantidad.text.toString().trim()

                    if(iArrayList.isEmpty()){

                        if(iNomPokemon.isEmpty()||iCantidad.isEmpty()){
                            validation(iNomPokemon,iCantidad)
                        }else{
                            val pk = Pokemons()
                            pk.nombrePokemon=iNomPokemon
                            pk.cantidad=iCantidad
                            iArrayList.add(pk)

                            val p = Persona()
                            p.nombre=iNnombre
                            p.arrayList=iArrayList
                            fireDatabase.child("Usuario").child(p.nombre).setValue(p)
                            limpiarCajas()
                        }
                    }else {
                        if (iNomPokemon.isEmpty() || iCantidad.isEmpty()) {
                            validation(iNomPokemon, iCantidad)
                        } else {
                            val p = Persona()
                            p.nombre = iNnombre
                            p.arrayList = iArrayList
                            val pk = Pokemons()
                            pk.nombrePokemon = iNomPokemon
                            pk.cantidad = iCantidad
                            iArrayList.add(pk)
                            fireDatabase.child("Usuario").child(p.nombre).setValue(p)
                            limpiarCajas()
                        }

                    }

                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                /*
                iArrayList.clear()
                for (snapshot in dataSnapshot.child(iNnombre).child("arrayList").children) {

                    var p:Pokemons= snapshot.getValue(Pokemons::class.java)!!
                    val pk = Pokemons()
                    pk.nombrePokemon=p.nombrePokemon
                    pk.cantidad=p.cantidad
                    iArrayList.add(p)
                }

                btn_mas.setOnClickListener {
                    iNomPokemon = txtPokemon.text.toString().trim()
                    iCantidad = txtCantidad.text.toString().trim()
                    if(iNomPokemon.isEmpty()||iCantidad.isEmpty()){
                        validation(iNomPokemon,iCantidad)
                    }else{
                        val pk = Pokemons()
                        pk.nombrePokemon=iNomPokemon
                        pk.cantidad=iCantidad
                        iArrayList.add(pk)
                        limpiarCajas()
                    }
                }

                btn_enviar.setOnClickListener {
                    iNomPokemon = txtPokemon.text.toString().trim()
                    iCantidad = txtCantidad.text.toString().trim()

                    if(iArrayList.isEmpty()){

                        if(iNomPokemon.isEmpty()||iCantidad.isEmpty()){
                            validation(iNomPokemon,iCantidad)
                        }else{
                            val pk = Pokemons()
                            pk.nombrePokemon=iNomPokemon
                            pk.cantidad=iCantidad
                            iArrayList.add(pk)

                            val p = Persona()
                            p.nombre=iNnombre
                            p.arrayList=iArrayList
                            fireDatabase.child("Usuario").child(p.nombre).setValue(p)
                            limpiarCajas()
                        }
                    }else{
                        val p = Persona()
                        p.nombre=iNnombre
                        p.arrayList=iArrayList
                        fireDatabase.child("Usuario").child(p.nombre).setValue(p)
                        limpiarCajas()
                    }

                }
                */
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

        fireDatabase.addChildEventListener(childEventListener)

    }


    private fun limpiarCajas(){
        txtPokemon.text!!.clear()
        Log.e(TAG, "Limpiar cajas !${txtPokemon.text}")
        txtCantidad.text!!.clear()
        Log.e(TAG, "Limpiar cajas !${txtCantidad.text}")
        iNomPokemon= ""
        iCantidad = ""
    }

    private fun validation(nombre : String, cantidad : String ) {

        if (nombre.equals("")|| nombre.isEmpty()){
            tilNPokemon.setError("Required")
        }else if(cantidad.equals("")|| cantidad.isEmpty()){
            tilCantidad.setError("Required")
        }
    }


}

