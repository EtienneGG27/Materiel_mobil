package fr.epf.projet_etienne_gauge

import retrofit2.http.GET

interface RestCountryApi {
        @GET("countries")
        suspend fun getAllCountries(): List<Country>
}
