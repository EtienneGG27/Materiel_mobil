package fr.epf.projet_etienne_gauge

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.launch

class CountryViewModel(context: Context) : ViewModel() {
        var allCountries: List<Country> = emptyList()
        var countries: List<Country> = emptyList()
        private val favorites: MutableList<Country> = mutableListOf()
        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        private val moshi: Moshi = Moshi.Builder().build()
        private val type = Types.newParameterizedType(List::class.java, Country::class.java)
        private val adapter = moshi.adapter<List<Country>>(type)

        init {
                loadFavorites()
        }

        fun loadAllCountries(onCountriesLoaded: (List<Country>) -> Unit, onError: (String) -> Unit) {
                viewModelScope.launch {
                        try {
                                val countriesResponse = RestCountriesApiService.api.getAllCountries()
                                Log.d("CountryViewModel", "Fetched countries: ${countriesResponse.size}")
                                allCountries = countriesResponse
                                countries = countriesResponse
                                onCountriesLoaded(countries)
                        } catch (e: Exception) {
                                Log.e("CountryViewModel", "Failed to load countries", e)
                                onError("Failed to load countries: ${e.message}")
                        }
                }
        }

        fun filterCountries(query: String, onCountriesFiltered: (List<Country>) -> Unit) {
                val filteredCountries = allCountries.filter {
                        (it.name?.contains(query, ignoreCase = true) ?: false) ||
                                (it.capital?.contains(query, ignoreCase = true) ?: false)
                }
                Log.d("CountryViewModel", "Filtered countries: ${filteredCountries.size}")
                countries = filteredCountries
                onCountriesFiltered(countries)
        }

        fun toggleFavorite(country: Country) {
                if (favorites.contains(country)) {
                        favorites.remove(country)
                } else {
                        favorites.add(country)
                }
                saveFavorites()
        }

        private fun saveFavorites() {
                val favoritesJson = adapter.toJson(favorites)
                sharedPreferences.edit().putString("favorites", favoritesJson).apply()
                Log.d("CountryViewModel", "Favorites saved: $favoritesJson")
        }

        private fun loadFavorites() {
                val favoritesJson = sharedPreferences.getString("favorites", null)
                if (favoritesJson != null) {
                        val loadedFavorites: List<Country>? = adapter.fromJson(favoritesJson)
                        if (loadedFavorites != null) {
                                favorites.clear()
                                favorites.addAll(loadedFavorites)
                        }
                }
                Log.d("CountryViewModel", "Favorites loaded: $favoritesJson")
        }

        fun getFavorites(): List<Country> {
                return favorites
        }
}
