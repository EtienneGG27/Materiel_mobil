package fr.epf.projet_android_guilhem_nils

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class CountryDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        val viewModelFactory = CountryViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CountryViewModel::class.java)

        val country = intent.getSerializableExtra("country") as Country

        val countryNameTextView = findViewById<TextView>(R.id.country_name)
        countryNameTextView.text = country.name

        val countryFlagImageView = findViewById<ImageView>(R.id.country_flag)
        Glide.with(this).load(country.flags.png).into(countryFlagImageView)

        val countryCapitalTextView = findViewById<TextView>(R.id.country_capital)
        countryCapitalTextView.text = country.capital ?: "Aucune capitale attitrée"

        val countryPopulationTextView = findViewById<TextView>(R.id.country_population)
        countryPopulationTextView.text = country.population.toString()

        val countryAreaTextView = findViewById<TextView>(R.id.country_area)
        countryAreaTextView.text = country.area.toString()

        val countryLanguagesTextView = findViewById<TextView>(R.id.country_languages)
        countryLanguagesTextView.text = country.languages.joinToString(", ") { it.name }

        val favoriteButton = findViewById<ImageButton>(R.id.favoriteButton)
        updateFavoriteButton(favoriteButton, country)

        favoriteButton.setOnClickListener {
            viewModel.toggleFavorite(country)
            updateFavoriteButton(favoriteButton, country)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun updateFavoriteButton(button: ImageButton, country: Country) {
        val isFavorite = viewModel.getFavorites().contains(country)
        button.setImageResource(
            if (isFavorite) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )
    }
}
