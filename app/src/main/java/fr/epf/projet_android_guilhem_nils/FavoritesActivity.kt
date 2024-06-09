package fr.epf.projet_android_guilhem_nils

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {

    private lateinit var countryAdapter: CountryAdapter
    private val viewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        setupRecyclerView()
        loadFavorites()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        countryAdapter = CountryAdapter(::onCountryClicked, ::onFavoriteClicked)
        recyclerView.adapter = countryAdapter
    }

    private fun loadFavorites() {
        val favorites = viewModel.getFavorites()
        if (favorites.isNotEmpty()) {
            countryAdapter.updateCountries(favorites)
        } else {
            Toast.makeText(this, "No favorites yet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCountryClicked(country: Country) {
        // Handle country click
    }

    private fun onFavoriteClicked(country: Country) {
        viewModel.toggleFavorite(country)
        loadFavorites()  // Refresh the list of favorites
    }
}
