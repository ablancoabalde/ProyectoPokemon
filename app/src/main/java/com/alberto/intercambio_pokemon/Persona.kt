package com.alberto.intercambio_pokemon

/**
 * Clase persona
 */
class Persona {

     var nombre : String = ""
     var arrayList: MutableList<Pokemons> = mutableListOf()

     override fun toString(): String {
          return " Nombre: '$nombre'\n ${arrayList.toString()}"
     }

}