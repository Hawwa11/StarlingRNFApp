package com.example.android.starlingrnfapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class PDPA extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pdpa);
            PDFView pdfView=findViewById(R.id.pdfView);

            pdfView.fromAsset("pdpa_starling.pdf")
                    .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)

                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen

                    .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
                    .pageSnap(false) // snap pages to screen boundaries
                    .pageFling(false) // make a fling change only a single page like ViewPager
                    .nightMode(false) // toggle night mode
                    .load();

        }
    }
