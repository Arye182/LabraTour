package com.example.labratour.presentation.ui.general

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.labratour.R
import kotlinx.android.synthetic.main.fragment_about.*
import us.feras.mdv.MarkdownView

class AboutFragment : Fragment(com.example.labratour.R.layout.fragment_about) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val markdownPath = "file:///android_asset/README.md"
        val markdownView: MarkdownView = markdown_about
        markdownView.loadMarkdownFile(markdownPath)
    }
}
