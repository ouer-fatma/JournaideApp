package tn.esprit.journaideapp.view.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import tn.esprit.journaideapp.R
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class RecordFragment : Fragment () {

    private lateinit var btnButton: Button
    private lateinit var tvText: TextView

    private val RQ_SPEECH_REC = 102

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_record, container, false)

        btnButton = view.findViewById(R.id.btn_button)
        tvText = view.findViewById(R.id.tv_text)

        btnButton.setOnClickListener {
            askSpeechInput()
        }

        return view

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            tvText.text = result?.get(0).toString()
        }
    }

    private fun askSpeechInput() {
        context?.let {
            if (!SpeechRecognizer.isRecognitionAvailable(it)) {
                Toast.makeText(it, "Speech recognition is not available", Toast.LENGTH_SHORT).show()
            } else {
                val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!")
                startActivityForResult(i, RQ_SPEECH_REC)
            }
        }
    }
}