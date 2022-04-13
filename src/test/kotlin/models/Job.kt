package models

import com.fasterxml.jackson.annotation.JsonProperty

data class Job(
    @JsonProperty("nasa-modis:1")
    val nasaModis: Pleiades,

    @JsonProperty("pansharpen:1")
    val sharpening: Sharpening
)

data class Pleiades(
    val ids: List<String>? = null,

    val time: String,

    val limit: Int,

    val zoom_level: Int,

    val imagery_layers: List<String>?,

    val bbox: List<Double>
)

data class Sharpening(
    val strength: String,
)