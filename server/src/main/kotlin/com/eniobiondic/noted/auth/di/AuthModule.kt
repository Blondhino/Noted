package com.eniobiondic.noted.auth.di

import com.eniobiondic.noted.auth.VerifyToken
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun authModule() = module {
    single {
        val firebaseCredentials = System.getenv(FIREBASE_CREDENTIALS)
            ?: error("Firebase credentials not found")

        val decodedCredentials = Base64.decode(firebaseCredentials).inputStream()
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(decodedCredentials))
            .build()
        FirebaseApp.initializeApp(options)
    }
    single { FirebaseAuth.getInstance(get()) }
    single { VerifyToken(get()) }
}

private const val FIREBASE_CREDENTIALS = "FIREBASE_CREDENTIALS"
