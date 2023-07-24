package com.neilturner.aerialviews.utils

import android.media.MediaDataSource
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class SambaMediaDataSource: MediaDataSource() {

    override fun readAt(position: Long, buffer: ByteArray?, offset: Int, size: Int): Int {
        return 0
    }

    override fun getSize(): Long {
        return 0
    }

    override fun close() {

    }
}