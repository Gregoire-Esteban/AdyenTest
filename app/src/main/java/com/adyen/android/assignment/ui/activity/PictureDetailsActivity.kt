package com.adyen.android.assignment.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.imageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.adyen.android.assignment.DATE_PATTERN
import com.adyen.android.assignment.databinding.ActivityPictureDetailsBinding
import com.adyen.android.assignment.domain.model.AstronomyPicture
import java.time.format.DateTimeFormatter

class PictureDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPictureDetailsBinding
    private var backgroundLoadingDisposable : Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        val retrievedPicture = intent.extras?.takeIf { it.containsKey(EXTRA__PICTURE) }
            ?.getParcelable(EXTRA__PICTURE) as? AstronomyPicture
        retrievedPicture?.let { initContent(it) }
    }

    private fun initContent(astronomyPicture: AstronomyPicture) {
        with(binding) {
            val dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
            pictureDate.text = astronomyPicture.date.format(dateFormatter)
            pictureTitle.text = astronomyPicture.title
            pictureDescription.text = astronomyPicture.explanation
            val backgroundRequest = ImageRequest.Builder(this@PictureDetailsActivity)
                .data(astronomyPicture.hdUrl)
                .target { drawable ->
                    root.background = drawable
                }
                .build()
            backgroundLoadingDisposable = imageLoader.enqueue(backgroundRequest)
        }
    }

    override fun onStop() {
        super.onStop()
        backgroundLoadingDisposable?.takeUnless { it.isDisposed }?.dispose()
    }

    companion object {
        private const val EXTRA__PICTURE = "EXTRA__PICTURE"
    }
}