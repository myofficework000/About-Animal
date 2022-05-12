package com.example.aboutanimals

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.aboutanimals.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callAPI()

        val firstName = "abhishek"
        val lastName = "Pathak"

        var name = "my name is ${firstName} ${lastName} "

        binding.nextButton.setOnClickListener {
            binding.loadingSpinner.visibility = View.VISIBLE
            binding.loadingSpinner2.visibility = View.VISIBLE
            binding.loadingSpinner3.visibility = View.VISIBLE
            callAPI()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private fun callAPI() {
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            Constants.BASE_URL,
            { apiResponse: String ->
                val typeToken = object : TypeToken<Animal>() {}
                val gson = Gson()
                try {
                    val animal: Animal = gson.fromJson(apiResponse, typeToken.type)

                    binding.apply {
                        animal.apply {
                            animalName.text = animal.name
                            fun htmlManipulation() {
                                val t1: String = getColoredSpanned(
                                    "Animal found at - ",
                                    getColor(R.color.text_color_secondary)
                                )
                                val r1: String =
                                    getColoredSpanned(geo_range.toString(), getColor(R.color.red))

                                val t2: String = getColoredSpanned(
                                    "Habitat - ",
                                    getColor(R.color.text_color_secondary)
                                )
                                val r2: String =
                                    getColoredSpanned(habitat.toString(), getColor(R.color.red))

                                val t3: String = getColoredSpanned(
                                    "Animal type - ",
                                    getColor(R.color.text_color_secondary)
                                )
                                val r3: String =
                                    getColoredSpanned(animal_type.toString(), getColor(R.color.red))

                                val t4: String = getColoredSpanned(
                                    "Lifespan - ",
                                    getColor(R.color.text_color_secondary)
                                )
                                val r4: String =
                                    getColoredSpanned(lifespan.toString(), getColor(R.color.red))

                                animalAnimalGeoRange.text = Html.fromHtml(t1 + r1)
                                animalHabitat.text = Html.fromHtml(t2 + r2)
                                animalAnimalType.text = Html.fromHtml(t3 + r3)
                                animalLifespan.text = Html.fromHtml(t4 + r4)
                            }
                            htmlManipulation()
                        }
                        if (animal.geo_range != null) {
                            animalAnimalGeoRange.visibility = View.VISIBLE
                        }
                        if (animal.habitat != null) {
                            animalHabitat.visibility = View.VISIBLE
                        }
                        if (animal.animal_type != null) {
                            animalAnimalType.visibility = View.VISIBLE
                        }
                        if (animal.lifespan != null) {
                            animalLifespan.visibility = View.VISIBLE
                        }

                        loadingSpinner.visibility = View.GONE
                        loadingSpinner2.visibility = View.GONE
                        loadingSpinner3.visibility = View.GONE
                        Glide.with(this@MainActivity).load(animal.image_link)
                            .into(imageOfAnimal)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    binding.loadingSpinner.visibility = View.GONE
                }
            }, { error ->
                binding.loadingSpinner.visibility = View.GONE
                error.printStackTrace()
                Toast.makeText(this, "Error is $error", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }

    private fun getColoredSpanned(text: String, color: Int): String {
        return "<font color=$color>$text</font>"
    }
}