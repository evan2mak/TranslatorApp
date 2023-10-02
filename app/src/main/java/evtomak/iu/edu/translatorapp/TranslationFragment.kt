package evtomak.iu.edu.translatorapp

import TranslationViewModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import evtomak.iu.edu.translatorapp.databinding.FragmentTranslationBinding

class TranslationFragment : Fragment() {
    private var _binding: FragmentTranslationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TranslationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTranslationBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(TranslationViewModel::class.java)

        val translationEditText = binding.translationEditText

        // Add a TextWatcher to the EditText for real-time translation
        translationEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Before text changes, do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Text is changing, identify and translate it
                val text = s.toString()
                viewModel.sourceTextLiveData.postValue(text)
                viewModel.identifyAndTranslateText(text)
            }

            override fun afterTextChanged(s: Editable?) {
                // After text changes, do nothing
            }
        })

        // Observe changes in the translated text and update the UI
        viewModel.translatedTextLiveData.observe(viewLifecycleOwner) { translatedText ->
            binding.translationTextView.text = translatedText
        }

        return view
    }
}
