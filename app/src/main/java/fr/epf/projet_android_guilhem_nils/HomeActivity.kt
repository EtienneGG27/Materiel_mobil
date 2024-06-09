package fr.epf.projet_android_guilhem_nils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    private lateinit var countryAdapter: CountryAdapter
    private val viewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val searchButton = findViewById<ImageButton>(R.id.search_county_button)
        val searchEditText = findViewById<EditText>(R.id.home_search_country)
        val favoritesButton = findViewById<ImageButton>(R.id.favorites_button)

        searchButton.setOnClickListener {
            val searchText = searchEditText.text.toString()
            performSearch(searchText)
        }

        favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
        loadCountries()
        viewModel.init(this)  // Initialize ViewModel with context for SharedPreferences
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        countryAdapter = CountryAdapter(::onCountryClicked, ::onFavoriteClicked)
        recyclerView.adapter = countryAdapter
    }

    private fun loadCountries() {
        viewModel.loadAllCountries(
            onCountriesLoaded = { countries ->
                runOnUiThread {
                    countryAdapter.updateCountries(countries)
                }
            },
            onError = { errorMessage ->
                runOnUiThread {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun onCountryClicked(country: Country) {
        val intent = Intent(this, CountryDetailsActivity::class.java)
        intent.putExtra("country", country)
        startActivity(intent)
    }

    private fun onFavoriteClicked(country: Country) {
        viewModel.toggleFavorite(country)
        Toast.makeText(this, "${country.name} ajouté aux favoris", Toast.LENGTH_SHORT).show()
    }

    private fun performSearch(query: String) {
        viewModel.filterCountries(query) { filteredCountries ->
            runOnUiThread {
                countryAdapter.updateCountries(filteredCountries)
            }
        }
    }
}
