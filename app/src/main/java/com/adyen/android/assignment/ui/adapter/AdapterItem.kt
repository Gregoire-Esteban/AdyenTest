package com.adyen.android.assignment.ui.adapter

import androidx.annotation.StringRes

interface AdapterItem

class HeaderItem(@StringRes val titleRes : Int) : AdapterItem
