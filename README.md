# C323 Project 5 - Translation App - Evan Tomak
This app is a translation app that requires two selections: a source language and a target language. This app allows the user to translate the source language to a target language of their choosing. The functionality is described in more detail below:

[X] The user opens the app.
[X] The user can select the source language by clicking on radio buttons for English, Spanish, or German.
[X] The user can select the target language by clicking on radio buttons for Spanish, German, or English.
[X] If the user does not select a source language and begins typing, the user will see a message that reads: "Select a source language and type your message using that language."
[X] If the user does not select a target language but has a source language selected and begins typing, the user will see a message that reads: "Make sure both buttons are selected."
[X] If the user does not type in the correct language that they have selected as a source language, then the user will see a message that reads: "Can't identify language."
[X] If the user switches the source language to a new language after typing, the same error message is thrown.
[X] If the user switches to a new target language, the input is translated to the new target language.
[X] If the source language and target language are the same, it handles the translation correctly by showing the input ("Hello" = "Hello" when both are English).
[X] If everything is handled correctly with no errors, the input will be translated in real-time.

The following functions/extensions are implemented:

# MainActivity.kt

This class sets up the UI and handles interactions to select source and target languages. It communicates with the TranslationViewModel to perform language identification and translation.

onCreate:
This function initializes the UI components, sets up the ViewModel, and attaches listeners to the source and target language radio buttons.

# TranslationFragment.kt

This class is a fragment that displays translation results and allows real-time text translation. It interacts with the TranslationViewModel to perform language identification and translation.

onCreateView:
This function inflates the layout, sets up the ViewModel, and adds a TextWatcher to the EditText user input. It monitors changes in the input text, triggers real-time translation, and observes changes in the translated text to update the UI accordingly.

# TranslationViewModel.kt

This class is the heart of the translator app. It is responsible for managing translation-related data and operations. It interacts with various ML Kit components to identify languages and perform translations while providing updates to LiveData for the UI.

identifyAndTranslateText(text: String):
This function performs language identification and translation based on the identified language. If language identification is successful, it checks the identified language code. If the language code is "und" (undetermined), it means that the language couldn't be identified, and a message is posted to LiveData indicating this. If the identified language matches the sourceLanguage, it calls translateText(text) to initiate the translation. If the identified language doesn't match the sourceLanguage, it posts a message indicating a language mismatch.

identifySourceLanguage():
This function identifies the source language from the text in sourceTextLiveData and throws appropriate errors if needed. 

translateText(text: String):
This function performs the translation of the input text. It constructs TranslatorOptions with the specified source and target languages. It checks if the translation model is downloaded using downloadModelIfNeeded(conditions). This ensures that the translation model is available for use. If the model is downloaded successfully, it translates the input text using translator.translate(text) and posts the translated text to LiveData. If there is a failure during translation, it posts a "Translation failed" message to LiveData.

translateText():
This is a helper function that simplifies the translation process for use from the main activity. It retrieves the text to translate from sourceTextLiveData and calls the translateText(text) function to initiate the translation process.

# Video Walkthrough

Here's a walkthrough of implemented user stories:

![studio64_c4O23m3ZGe](https://github.com/evan2mak/TranslatorApp/assets/128643914/f0ea8d52-b656-4ad4-ab53-fbe619302a54)

# Notes

The biggest challenge I faced was figuring out the logic of identifying the languages and then connecting that to a translation functionality. I also struggled with the fragment container at times because I could not get it to communicate properly with the fragment and view model.

# License

Copyright 2023 Evan Tomak.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express implied.

See the License for the specific language governing permissions and limitations under the License.







