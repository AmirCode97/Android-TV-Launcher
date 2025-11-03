package com.smarttv.launcher.presenters

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.smarttv.launcher.R
import com.smarttv.launcher.models.AppModel

class CardPresenter : Presenter() {

    private val CARD_WIDTH = 313
    private val CARD_HEIGHT = 176

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val cardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            setBackgroundColor(ContextCompat.getColor(context, R.color.default_background))
        }
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val appModel = item as AppModel
        val cardView = viewHolder.view as ImageCardView

        cardView.titleText = appModel.appName
        cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)

        if (appModel.icon != null) {
            cardView.mainImageView.setImageDrawable(appModel.icon)
        } else if (appModel.iconResId != null) {
            Glide.with(viewHolder.view.context)
                .load(appModel.iconResId)
                .centerCrop()
                .into(cardView.mainImageView)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}
