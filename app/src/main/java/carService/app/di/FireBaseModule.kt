package carService.app.di

import android.content.Context
import carService.app.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FireBaseModule(private val context: Context) {

    val options = FirebaseOptions.Builder()
        .setApplicationId(context.getString(R.string.google_app_id)) // Required for Analytics.
        .setProjectId(context.getString(R.string.project_id)) // Required for Firebase Installations.
        .setApiKey(context.getString(R.string.google_api_key)) // Required for Auth.
        .build()

    var firebaseApp = FirebaseApp.initializeApp(context, options, "CarApp")

    var provideFireBaseAuth: FirebaseAuth =
        FirebaseAuth.getInstance()

    var provideGso = GoogleSignInOptions.Builder(
        GoogleSignInOptions.DEFAULT_SIGN_IN
    )
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    var provideGoogleClient: GoogleSignInClient =
        GoogleSignIn.getClient(context, provideGso)

    var provideFirebaseFirestore = FirebaseFirestore.getInstance()
    var provideFirebaseStorageReference = FirebaseStorage.getInstance().reference
}