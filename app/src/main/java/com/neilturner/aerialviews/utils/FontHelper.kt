package com.neilturner.aerialviews.utils

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.TypefaceCompat
import com.neilturner.aerialviews.R
import com.neilturner.aerialviews.models.prefs.GeneralPrefs

object FontHelper {

    fun getTypeface(context: Context): Typeface {
        val font = if (GeneralPrefs.fontTypeface == "open-sans") {
            ResourcesCompat.getFont(context, R.font.opensans)
        } else {
            Typeface.create("san-serif", Typeface.NORMAL)
        }
        return TypefaceCompat.create(context, font, GeneralPrefs.fontWeight.toInt(), false)
    }
}
