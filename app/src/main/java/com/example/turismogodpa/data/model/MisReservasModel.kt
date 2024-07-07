package com.example.turismogodpa.data.model

data class MisReservasModel(
    val id : String = "",
    val description: String = "",
    val lugar: String = "",
    val date: String = "",
    val idCompany: String = "",
    val image: String = "",
    val price: String = "",
    val state: String = "",
    val hora: String = "",
    val titulo: String = "",
    val type: String = "",
    val users: String = ""
)

/*

document["Description"].toString(), //el name entre comillas es el nombre del campo en la base de datos
                        document["Lugar"].toString(),
                        document["date"].toString(),
                        document["idCompany"].toString(),
                        document["image"].toString(),
                        document["price"].toString(),
                        document["state"].toString(),
                        document["hora"].toString(),
                        document["titulo"].toString(),
                        document["type"].toString()

 */