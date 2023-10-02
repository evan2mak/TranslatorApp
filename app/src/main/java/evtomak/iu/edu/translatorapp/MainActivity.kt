package evtomak.iu.edu.translatorapp

import TranslationViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import evtomak.iu.edu.translatorapp.databinding.ActivityMainBinding

// This class sets up the UI and handles interactions to select source and target languages
// This communicates with the TranslationViewModel to perform language identification and translation
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TranslationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(TranslationViewModel::class.java)

        // Add RadioGroup listeners to set the source and target languages
        val sourceLanguageRadioGroup = binding.sourceLanguageRadioGroup
        val targetLanguageRadioGroup = binding.targetLanguageRadioGroup

        sourceLanguageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.EnglishLanguageButton -> {
                    viewModel.sourceLanguage = "en" // Set source language to English
                    viewModel.identifySourceLanguage() // Identify the source language
                }
                R.id.SpanishLanguageButton -> {
                    viewModel.sourceLanguage = "es" // Set source language to Spanish
                    viewModel.identifySourceLanguage() // Identify the source language
                }
                R.id.GermanLanguageButton -> {
                    viewModel.sourceLanguage = "de" // Set source language to German
                    viewModel.identifySourceLanguage() // Identify the source language
                }
            }
        }

        targetLanguageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.SpanishTranslationButton -> {
                    viewModel.targetLanguage = "es" // Set target language to Spanish
                    viewModel.translateText() // Translate text
                }
                R.id.GermanTranslationButton -> {
                    viewModel.targetLanguage = "de" // Set target language to German
                    viewModel.translateText() // Translate text
                }
                R.id.EnglishTranslationButton -> {
                    viewModel.targetLanguage = "en" // Set target language to English
                    viewModel.translateText() // Translate text
                }
            }
        }
    }
}
