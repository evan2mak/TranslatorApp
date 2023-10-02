package evtomak.iu.edu.translatorapp

import TranslationViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import evtomak.iu.edu.translatorapp.databinding.ActivityMainBinding

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
                    viewModel.sourceLanguage = "en"
                    viewModel.identifySourceLanguage()
                }
                R.id.SpanishLanguageButton -> {
                    viewModel.sourceLanguage = "es"
                    viewModel.identifySourceLanguage()
                }
                R.id.GermanLanguageButton -> {
                    viewModel.sourceLanguage = "de"
                    viewModel.identifySourceLanguage()
                }
            }
        }

        targetLanguageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.SpanishTranslationButton -> {
                    viewModel.targetLanguage = "es"
                    viewModel.translateText()
                }
                R.id.GermanTranslationButton -> {
                    viewModel.targetLanguage = "de"
                    viewModel.translateText()
                }
                R.id.EnglishTranslationButton -> {
                    viewModel.targetLanguage = "en"
                    viewModel.translateText()
                }
            }
        }
    }
}
