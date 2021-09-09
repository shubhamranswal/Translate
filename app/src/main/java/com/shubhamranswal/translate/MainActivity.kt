package com.shubhamranswal.translate

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var etLangText:EditText
    lateinit var btnCheckNow:Button
    lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etLangText = findViewById(R.id.etLangText)
        btnCheckNow = findViewById(R.id.btnCheckNow)
        tvResult = findViewById(R.id.tvResult)

        btnCheckNow.setOnClickListener{
            val langText:String = etLangText.text.toString()
            if (langText == ""){
                //toast for entering input
            }else{
                translateText(langText)
            }
        }
    }

    private fun translateText(langText: String) {
        val languageIdentifier:LanguageIdentifier = LanguageIdentification.getClient()

        val languageCode = languageIdentifier.identifyLanguage(langText)
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.GERMAN)
            .build()
        val translator = Translation.getClient(options)
        lifecycle.addObserver(translator)

        val conditions = DownloadConditions.Builder().requireWifi().build()
        translator.downloadModelIfNeeded(conditions)
        translator.translate(langText)
            .addOnSuccessListener { translatedText ->
                // Translation successful.
                tvResult.text = "Results: ${translatedText}"
            }
            .addOnFailureListener { exception ->
                // Error.
                // ...
            }

    }
}