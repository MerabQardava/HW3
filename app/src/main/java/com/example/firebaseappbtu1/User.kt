package com.example.firebaseappbtu1

data class User(
    val name : String ?= null,
    val surname: String ?=null,
    val email : String ?= null,
    val uid : String ?= null,
    val link : String ?= null,
    val pn : String ?= null,

)

data class School(
    val students: List<User>
)
