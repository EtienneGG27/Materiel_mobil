package fr.epf.projet_android_guilhem_nils

import retrofit2.http.GET

interface RestCountryApi {
        @GET("countries")
        suspend fun getAllCountries(): List<Country>
}
