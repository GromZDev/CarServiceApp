package carService.app

import android.app.Application
import carService.app.di.appModule
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
        FirebaseFirestore.getInstance().firestoreSettings = settings
        Firebase.database.setPersistenceEnabled(true)
        startKoin {
            androidContext(this@App)
            modules(
                arrayListOf(
                    appModule
                )
            )
        }
    }
}