// imports
const express = require('express');
const app = express();
const bodyParser = require('body-parser');

// inicializamos la conexion con firebase
// necesitamos json con las credenciales 
var admin = require('firebase-admin');

// Directorio con el json que contiene las credenciales
var serviceAccount = require('./dbfirebasePokemon.json');


admin.initializeApp({

    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://intercambio-pokemon.firebaseio.com'
});

var db = admin.database();
var ref = db.ref("Usuario");
var resultadoNombreJugador = null;
var resultadoArraPokemon = null;
var arrayUsuarios=[];

// Funci�n usada solo una vez para cargar a todos los jugadores en un array y subscribirlos al topic
ref.once("value",function(snapshot){
  
    snapshot.forEach(function(userSnapshot) {
        userSnapshot.child("Usuario");
        // Key de la base de dato
        var token=userSnapshot.key;
        
        var arrayPokemonFB= userSnapshot.val().arrayList;
        var tama�oArray=arrayPokemonFB.length
        
        // Agregamos el token al Array
        arrayUsuarios.push(token);
        
        // Subscribimos los Tokens en un topic que se llame jugadores
        admin.messaging().subscribeToTopic(token, 'jugadores').then(function(response) {
            // Comprobaci�n que de se agregan correctamente
            console.log('Completada la subscripci�n:', response);
        })
    .catch(function(error) {
        console.log('Error al subscribirse:', error);
    });
    
    });
});

// Funci�n que es llamada cada vez que se modifique la FireBase
ref.on("child_changed", function(snapshot) {
    
    resultadoNombreJugador=snapshot.val().nombre;
    resultadoArraPokemon= snapshot.val().arrayList;
    
    var tama�oArray=resultadoArraPokemon.length;
    // Recogemos solo el �ltimo objeto introducido y lo metemos en una variable
    var array=resultadoArraPokemon[tama�oArray-1];
    
    var msg="Cantidad "+ array.cantidad[0] + " Pokemon "+ array.nombrePokemon
    
    // Llamada al topic
    var topic2 = 'jugadores';
    // Creamos el contenido de la notificaci�n a enviar
    var message = {
       
        notification: {
            title:'Pokemon a cambiar de ' + resultadoNombreJugador,
            body:msg
        },
        topic: topic2
       
    };

    // Enviamos la notificaci�n a todos los usuarios de la App
    admin.messaging().send(message)
        .then((response) => {

            console.log('Mensaje enviado:', response);
        })
        .catch((error) => {
            console.log('Error al enviar el mensaje:', error);
        });
        
}, function(errorObject) {
    console.log("The read failed: " + errorObject.code);
});