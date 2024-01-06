package com.example.madcamp_2

import java.io.Serializable

data class BoardModel(
    var author: String,
    var title: String,
    var context: String,
    var board: String
) : Serializable

data class BoardClassModel(
    var name: String,
    var creater: String
) : Serializable

data class BoardName(
    var name : String
)

data class Createboardclass(
    var newtitle: String,
    var creater: String
)

data class CreateboardclassResponse(
    var success: Boolean
)