package com.jokesapp.utils

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class CustomItemAnimator : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        // Customize the add animation here
        // You can use ObjectAnimator or other animation methods
        // Example:
        ViewCompat.animate(holder.itemView)
            .scaleX(0f)
            .scaleY(0f)
            .alpha(0f)
            .setDuration(addDuration)
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View) {}
                override fun onAnimationEnd(view: View) {
                    dispatchAddFinished(holder)
                    view.scaleX = 1f
                    view.scaleY = 1f
                    view.alpha = 1f
                }

                override fun onAnimationCancel(view: View) {
                    view.scaleX = 1f
                    view.scaleY = 1f
                    view.alpha = 1f
                }
            })
            .start()
        return true
    }
}