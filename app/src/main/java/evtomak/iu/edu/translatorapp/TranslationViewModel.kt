import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class TranslationViewModel : ViewModel() {
    var sourceLanguage: String = "" // Default source language (English)
    var targetLanguage: String = "" // Default target language (Spanish)

    val sourceTextLiveData = MutableLiveData<String>()
    val translatedTextLiveData = MutableLiveData<String>()

    fun identifyAndTranslateText(text: String) {
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(text)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    // Cannot identify language
                    translatedTextLiveData.postValue("Can't identify language.")
                }
                else {
                    if (languageCode == sourceLanguage) {
                        // Translate text if the identified language matches the source language
                        translateText(text)
                    }
                    else {
                        // Language does not match the source language
                        translatedTextLiveData.postValue("Select a source language and type your message using that language.")
                    }
                }
            }
            .addOnFailureListener {
                // Model couldn’t be loaded or other internal error.
                translatedTextLiveData.postValue("Make sure both buttons are selected.")
            }
    }

    fun identifySourceLanguage() {
        val textToIdentify = sourceTextLiveData.value ?: ""
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(textToIdentify)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    // Cannot identify language
                    translatedTextLiveData.postValue("Can't identify language.")
                } else {
                    if (languageCode == sourceLanguage) {
                        // Language matches the source language
                        translateText()
                    } else {
                        // Language does not match the source language
                        translatedTextLiveData.postValue("Language mismatch. Type your message in the selected language.")
                    }
                }
            }
            .addOnFailureListener {
                // Model couldn’t be loaded or other internal error.
                translatedTextLiveData.postValue("Make sure both buttons are selected.")
            }
    }

    private fun translateText(text: String) {
        val translatorOptions = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()
        val translator = Translation.getClient(translatorOptions)

        // Check if the translation model is downloaded
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully, start translation
                translator.translate(text)
                    .addOnSuccessListener { translatedText ->
                        // Update the LiveData with the translated text
                        translatedTextLiveData.postValue(translatedText)
                    }
                    .addOnFailureListener { exception ->
                        // Translation failed, handle the error
                        translatedTextLiveData.postValue("Translation failed.")
                    }
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                translatedTextLiveData.postValue("Make sure both buttons are selected.")
            }
    }

    fun translateText() {
        val textToTranslate = sourceTextLiveData.value ?: ""
        translateText(textToTranslate)
    }
}
