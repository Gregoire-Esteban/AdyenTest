package com.adyen.android.assignment.ui.activity

import android.os.Bundle
import coil.imageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.adyen.android.assignment.DATE_PATTERN
import com.adyen.android.assignment.databinding.ActivityPictureDetailsBinding
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.ui.ConnectionStatusProvider
import com.adyen.android.assignment.ui.ErrorType
import org.koin.android.ext.android.inject
import java.time.format.DateTimeFormatter

class PictureDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityPictureDetailsBinding
    private var backgroundLoadingDisposable : Disposable? = null
    private val connectionStatusProvider : ConnectionStatusProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureDetailsBinding.inflate(layoutInflater).apply {
            error = ErrorType.NO_ERROR
        }
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        val retrievedPicture = intent.extras?.takeIf { it.containsKey(EXTRA__PICTURE) }
            ?.getParcelable(EXTRA__PICTURE) as? AstronomyPicture
        retrievedPicture?.let { initContent(it) }
    }

    override fun onResume() {
        super.onResume()
        connectionStatusProvider.registerAdditionalCallback({
            binding.error = ErrorType.NO_ERROR
        }, {
            binding.error = ErrorType.NETWORK
        })
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
            errorLayout.errorButton.setOnClickListener {
                openSettings()
            }
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