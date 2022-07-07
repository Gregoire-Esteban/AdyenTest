package com.adyen.android.assignment.ui

import androidx.annotation.StringRes
import com.adyen.android.assignment.R

enum class ErrorType(
    @StringRes val titleRes : Int,
    @StringRes val messageRes : Int,
    @StringRes val buttonLabelRes : Int,
) {
    API(R.string.api_error_title, R.string.api_error_message, R.string.api_error_message),
    NETWORK(R.string.network_error_title, R.string.network_error_message, R.string.network_error_message),
}
