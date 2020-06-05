package com.giyadabi.appat_admin

data class Data(
    val id: String,
    val uid:String,
    val reportImageUrl:String,
    val rating: String,
    val location: String,
    val date: String,
    val description: String,
    val verification: Boolean
){
    constructor():this("","","","","","",
        "",true)
}