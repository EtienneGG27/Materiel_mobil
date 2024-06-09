package fr.epf.projet_android_guilhem_nils

import com.squareup.moshi.Json
import java.io.Serializable

data class Country(
    @Json(name = "name") val name: String,
    @Json(name = "capital") val capital: String?,
    @Json(name = "flags") val flags: Flags,
    @Json(name = "population") val population: Long,
    @Json(name = "area") val area: Double,
    @Json(name = "languages") val languages: List<Language>
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Country) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

data class Flags(
    @Json(name = "png") val png: String
) : Serializable

data class Language(
    @Json(name = "name") val name: String
) : Serializable
