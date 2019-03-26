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

// Función usada solo una vez para cargar a todos los jugadores en un array y subscribirlos al topic
ref.once("value",function(snapshot){
  
    snapshot.forEach(function(userSnapshot) {
        userSnapshot.child("Usuario");
        // Key de la base de dato
        var token=userSnapshot.key;
        
        var arrayPokemonFB= userSnapshot.val().arrayList;
        var tamañoArray=arrayPokemonFB.length
        
        // Agregamos el token al Array
        arrayUsuarios.push(token);
        
        // Subscribimos los Tokens en un topic que se llame jugadores
        admin.messaging().subscribeToTopic(token, 'jugadores').then(function(response) {
            // Comprobación que de se agregan correctamente
            console.log('Completada la subscripción:', response);
        })
    .catch(function(error) {
        console.log('Error al subscribirse:', error);
    });
    
    });
});

// Función que es llamada cada vez que se modifique la FireBase
ref.on("child_changed", function(snapshot) {
    
    resultadoNombreJugador=snapshot.val().nombre;
    resultadoArraPokemon= snapshot.val().arrayList;
    
    var tamañoArray=resultadoArraPokemon.length;
    // Recogemos solo el último objeto introducido y lo metemos en una variable
    var array=resultadoArraPokemon[tamañoArray-1];
    
    var msg="Cantidad "+ array.cantidad[0] + " Pokemon "+ array.nombrePokemon
    
    // Llamada al topic
    var topic2 = 'jugadores';
    // Creamos el contenido de la notificación a enviar
    var message = {
       
        notification: {
            title:'Pokemon a cambiar de ' + resultadoNombreJugador,
            body:msg
        },
        topic: topic2
       
    };

    // Enviamos la notificación a todos los usuarios de la App
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