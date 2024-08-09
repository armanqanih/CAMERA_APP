package org.lotka.xenonx.presentation.ui.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import dagger.hilt.android.AndroidEntryPoint

import android.content.pm.PackageManager

import androidx.compose.material3.ExperimentalMaterial3Api


import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.lotka.xenonx.presentation.screen.camera.CameraScreen
import org.lotka.xenonx.presentation.screen.camera.CameraViewModel

import org.lotka.xenonx.presentation.screen.camera.compose.Permissin


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    val viewModel by viewModels<CameraViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            if (!hasRequiredPermissions()) {
                ActivityCompat.requestPermissions(
                    this, Permissin.CAMERAX_PERMISSIONS, 0
                )
            }
        installSplashScreen()
        setContent {
            CameraScreen(
                applicationContext = applicationContext,
                viewModel = viewModel) }

        }



        private fun hasRequiredPermissions(): Boolean {
            return Permissin.CAMERAX_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        }


    }