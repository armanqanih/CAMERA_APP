package org.lotka.xenonx.presentation

import android.Manifest

sealed class Permissin {

    companion object {
        val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    }

}