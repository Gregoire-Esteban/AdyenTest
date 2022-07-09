package com.adyen.android.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import com.adyen.android.assignment.R
import com.adyen.android.assignment.databinding.DialogReorderBinding

class ReorderDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogReorderBinding
    private lateinit var onApplyClicked: (Boolean) -> Unit
    private lateinit var onResetClicked: () -> Unit
    private var isSortingByDate = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogReorderBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.ThemeOverlay_FullScreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARG__IS_SORTING_BY_DATE) }?.getBoolean(
            ARG__IS_SORTING_BY_DATE
        )?.let { this.isSortingByDate = it }
        with(binding) {
            if (isSortingByDate) {
                reorderByDate.isChecked = true
            } else {
                reorderByTitle.isChecked = true
            }
            sortingOrderSelection.setOnCheckedChangeListener { _, chosenId ->
                isSortingByDate = chosenId == R.id.reorder_by_date
            }
            applyButton.setOnClickListener {
                onApplyClicked(isSortingByDate)
            }
            resetButton.setOnClickListener {
                onResetClicked()
            }
        }
    }

    companion object {
        private const val ARG__IS_SORTING_BY_DATE = "ARG__IS_SORTING_BY_DATE"

        fun newInstance(
            isSortingByDate: Boolean = true,
            onApplyClicked: (Boolean) -> Unit,
            onResetClicked: () -> Unit
        ) = ReorderDialog().apply {
            arguments = bundleOf(ARG__IS_SORTING_BY_DATE to isSortingByDate)
            this.onApplyClicked = onApplyClicked
            this.onResetClicked = onResetClicked
        }
    }
}