package fr.epf.projet_android_guilhem_nils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CountryAdapter(
    private val onCountryClicked: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var countries: List<Country> = listOf()

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.countryName)
        val countryFlag: ImageView = itemView.findViewById(R.id.countryFlag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.countryName.text = country.name
        Glide.with(holder.itemView.context)
            .load(country.flags.png)
            .into(holder.countryFlag)
        holder.itemView.setOnClickListener {
            onCountryClicked(country)
        }
    }

    override fun getItemCount() = countries.size

    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries.sortedBy { it.name }
        notifyDataSetChanged()
    }
}
