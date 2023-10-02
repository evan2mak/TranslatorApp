import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

// This is a ViewModel class that manages translation-related data and operations
// It handles language identification, translation, and updates LiveData for UI
// The functions within the ViewModel perform language identification, translation, and error handling
class TranslationViewModel : ViewModel() {
    var sourceLanguage: String = ""
    var targetLanguage: String = ""

    val sourceTextLiveData = MutableLiveData<String>()
    val translatedTextLiveData = MutableLiveData<String>()

    // Identify the language and translate the text based on the identified language
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
                // Both buttons must be selected
                translatedTextLiveData.postValue("Make sure both buttons are selected.")
            }
    }

    // Identify the source language and throw appropriate errors
    fun identifySourceLanguage() {
        val textToIdentify = sourceTextLiveData.value ?: ""
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(textToIdentify)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und" && textToIdentify != "") {
                    // Cannot identify language
                    translatedTextLiveData.postValue("Can't identify language.")
                }
                else if (textToIdentify != ""){
                    if (languageCode == sourceLanguage) {
                        // Language matches the source language
                        translateText()
                    }
                    else {
                        // Language does not match the source language
                        translatedTextLiveData.postValue("Language mismatch. Type your message in the selected language.")
                    }
                }
            }
            .addOnFailureListener {
                // Both buttons must be selected
                translatedTextLiveData.postValue("Make sure both buttons are selected.")
            }
    }

    // Function that translates the text
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
                // Both buttons must be pressed
                translatedTextLiveData.postValue("Make sure both buttons are selected.")
            }
    }

    // Helper function for main activity call
    fun translateText() {
        val textToTranslate = sourceTextLiveData.value ?: ""
        translateText(textToTranslate)
    }
}
