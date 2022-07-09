package com.adyen.android.assignment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.adyen.android.assignment.DATE_PATTERN
import com.adyen.android.assignment.databinding.AstronomyPictureItemBinding
import com.adyen.android.assignment.databinding.HeaderItemBinding
import com.adyen.android.assignment.domain.model.AstronomyPicture
import java.time.format.DateTimeFormatter

class AstronomyPictureAdapter(
    private var elementList: List<AdapterItem>,
    private val callback: AstronomyPictureItemCallback
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val ITEM_TYPE__HEADER = 100
        const val ITEM_TYPE__PICTURE = 200
    }

    override fun getItemViewType(position: Int): Int {
        return if (elementList[position] is HeaderItem) ITEM_TYPE__HEADER else ITEM_TYPE__PICTURE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == ITEM_TYPE__HEADER) {
            HeaderViewHolder(
                parent.context,
                HeaderItemBinding.inflate(LayoutInflater.from(parent.context))
            )
        } else {
            AstronomyPictureViewHolder(
                AstronomyPictureItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                ), callback
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(elementList[position])
    }

    override fun getItemCount() = elementList.size

    fun updateList(newElements: List<AdapterItem>) {
        elementList = newElements
        notifyDataSetChanged()
    }
}

class AstronomyPictureViewHolder(
    private val binding: AstronomyPictureItemBinding,
    private val callback: AstronomyPictureItemCallback
) : BaseViewHolder(binding.root) {
    override fun bind(element: AdapterItem) {
        if (element is AstronomyPicture) {
            with(binding) {
                previewImage.load(element.url) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                pictureTitle.text = element.title
                val dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
                pictureDate.text = element.date.format(dateFormatter)
                root.setOnClickListener {
                    callback.onItemClicked(element)
                }
            }
        }
    }
}

class HeaderViewHolder(private val context: Context, private val binding: HeaderItemBinding) :
    BaseViewHolder(binding.root) {
    override fun bind(element: AdapterItem) {
        if (element is HeaderItem) {
            binding.headerTitle.text = context.getString(element.titleRes)
        }
    }
}