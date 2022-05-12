package com.example.aboutanimals

data class Animal(
    var name: String? = null,
    var latin_name: String? = null,
    var animal_type: String? = null,
    var active_ime: String? = null,
    var length_in: String? = null,
    var length_max: String? = null,
    var weightMin: String? = null,
    var weightMax: String? = null,
    var lifespan: String? = null,
    var habitat: String? = null,
    var diet: String? = null,
    var geo_range: String? = null,
    val image_link: String,
    var id: Int? = null
)