package com.alberto.intercambio_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_interfaz.*

/**
 * Clase principal en la que se añaden pokemons a la base de datos
 */
class Interfaz : AppCompatActivity() {

    val TAG = "AppCompatActivity"

    private var FCMToken: String? = null
    // Referencia a la base de datos del proyecto en firebase
    private var fireDatabase= FirebaseDatabase.getInstance().getReference()

    // Variables generales
    var iNnombre: String = ""
    var iNomPokemon: String = ""
    var iCantidad: String = ""
    var iArrayList: MutableList<Pokemons> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz)

        // Obtenemos el token del móvil
        FCMToken = FirebaseInstanceId.getInstance().token
        // Guardamos el token del movil en una variable global
        Variables.MyTokenMovil=FCMToken.toString()
        // Insertamos el nombre de la caja de texto del MainActivity a traves de la variable global
        iNnombre= Variables.cajaUsuario

        // Botón que llama a la ventana ListaUsuarios
        btn_ver.setOnClickListener{
            val lPokemon = Intent(this, ListaUsuarios::class.java)
            startActivity(lPokemon)
        }

        initListener()
    }

    private fun initListener() {

        val childEventListener = object : ChildEventListener {
            /**
             * Método para agregar un nuevo pokemon al usuario
             */
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // Borramos la lista de Pokemons, para que cuando guarde pokemons de seguido no se dupliquen
                iArrayList.clear()

                // Hacemos un cursor en la base de datos para recoger el arraylist que contiene todos los pokemons
                // con sus cantidades
                for (snapshot in dataSnapshot.child(Variables.MyTokenMovil).child("arrayList").children) {
                    //  Metemos el resultado de la consulta en una variable pokemon de la clase Pokemon
                    var p:Pokemons= snapshot.getValue(Pokemons::class.java)!!
                    // Creamos un objeto pokemon
                    val pk = Pokemons()
                    // Hacemos los Set de la clase Pokemon
                    pk.nombrePokemon=p.nombrePokemon
                    pk.cantidad=p.cantidad
                    // Agregamos a la MutableList la consulta pokemon
                    iArrayList.add(p)
                }
                // Botón que almacena una lista de pokemons introducidos por el usuario antes de darle a enviar para
                // subirlos todos juntos
                btn_mas.setOnClickListener {
                    iNomPokemon = txtPokemon.text.toString().trim()
                    iCantidad = txtCantidad.text.toString().trim()

                    // Condición si  la caja de texto está vacía, llama al metodo validation, si no guarda pokemon en
                    // en el MutableList y llama a la funcion de limpiar las cajas de texto
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

                // Botón que envía los datos necesarios para almacenar un nuevo usario en la base de datos

                btn_enviar.setOnClickListener {
                    iNomPokemon = txtPokemon.text.toString().trim()
                    iCantidad = txtCantidad.text.toString().trim()

                    // Comprobamos si el MutableList está vacio, para saber si es el primer ingreso del usuario o si
                    // ya estaba antes y tenía registros guardados en la base de datos y estos no se pierdan
                    if(iArrayList.isEmpty()){

                        // Condición si  la cajas de texto está vacía, llama al metodo validation, si no guarda pokemon en
                        // en el MutableList y todo esto con la información del usuario nombre y como clave principal el token
                        // y llama a la funcion de limpiar las cajas de texto
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
                            fireDatabase.child("Usuario").child(FCMToken.toString()).setValue(p)
                            limpiarCajas()
                        }

                    }else {
                        // Condición si  la cajas de texto está vacía, llama al metodo validation, si no guarda pokemon en
                        // en el MutableList y todo esto con la información del usuario nombre y como clave principal el token
                        // y llama a la funcion de limpiar las cajas de texto
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
                            fireDatabase.child("Usuario").child(FCMToken.toString()).setValue(p)
                            limpiarCajas()
                        }

                    }

                }
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

        fireDatabase.addChildEventListener(childEventListener)

    }

    /**
     * Metodo que limpia las cajas de texto
     */
    private fun limpiarCajas(){
        txtPokemon.text!!.clear()
        Log.e(TAG, "Limpiar cajas !${txtPokemon.text}")
        txtCantidad.text!!.clear()
        Log.e(TAG, "Limpiar cajas !${txtCantidad.text}")
        iNomPokemon= ""
        iCantidad = ""
    }

    /**
     *Metodo que muestra un texto de nombre requerido debajo de la caja de texto
     */
    private fun validation(nombre : String, cantidad : String ) {

        if (nombre.equals("")|| nombre.isEmpty()){
            tilNPokemon.setError("Nombre Pokemon requerido")
        }else if(cantidad.equals("")|| cantidad.isEmpty()){
            tilCantidad.setError("Cantidad requerida")
        }
    }


}

