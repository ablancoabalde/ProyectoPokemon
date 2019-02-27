package com.alberto.intercambio_pokemon

class Persona {

     var nombre : String = ""
     var arrayList: MutableList<Pokemons> = mutableListOf()
     override fun toString(): String {
          return "nombre='$nombre'\n ${arrayList.toString()}"
     }

}