package fr.epf.projet_android_guilhem_nils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide

class CountryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        val country = intent.getSerializableExtra("country") as Country
        val countryNameTextView = findViewById<TextView>(R.id.country_name)
        countryNameTextView.text = country.name
        val countryFlagImageView = findViewById<ImageView>(R.id.country_flag)
        Glide.with(this).load(country.flags.png).into(countryFlagImageView)
        val countryCapitalTextView = findViewById<TextView>(R.id.country_capital)
        countryCapitalTextView.text = country.capital
        val countryPopulationTextView = findViewById<TextView>(R.id.country_population)
        countryPopulationTextView.text = country.population.toString()
        val countryAreaTextView = findViewById<TextView>(R.id.country_area)
        countryAreaTextView.text = country.area.toString()
        val countryLanguagesTextView = findViewById<TextView>(R.id.country_languages)
        countryLanguagesTextView.text = country.languages.joinToString(", ") { it.name }
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
