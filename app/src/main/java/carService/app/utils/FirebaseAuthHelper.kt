package carService.app.utils

import carService.app.data.model.UserData
import carService.app.di.FireBaseModule
import carService.app.repo.personal.Repository
import carService.app.utils.CommonConstants.USER
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class FirebaseAuthHelper : KoinComponent {
    companion object {
        val instance = FirebaseAuthHelper()
    }

    private val auth: FirebaseAuth by lazy { Firebase.auth }

    private val repository: Repository by inject()
    private val fbase by inject<FireBaseModule>()

    private lateinit var db: FirebaseFirestore

    var currentUser: FirebaseUser? = null
        get() = auth.currentUser

    var isAuthorized: Boolean = false
        get() = auth.currentUser != null

    var fireBaseAuth: FirebaseAuth =
        FirebaseAuth.getInstance()

    fun check(hasAuth: (Boolean) -> Unit) {
        auth.addAuthStateListener { fauth -> hasAuth(fauth.currentUser != null) }
    }

    fun getUser(): UserData? {
        val user = auth.currentUser
        if (user != null) {
            val userInitial = UserData(
                uid = user.uid,
                nickName = user.displayName.orEmpty(),
                email = user.email.orEmpty()
            )
            userCommon(userInitial)
            return userInitial
        }
        return null
    }

//    fun getUser(): UserData? {
//        var userInitial = UserData()
//        db = FirebaseFirestore.getInstance()
//        auth.currentUser?.uid?.let { db.collection("users").document(it)
//            .get()
//            .addOnSuccessListener { link ->
//                val nUser = link.toObject<UserData>()
//                val user = auth.currentUser
//                if (user != null) {
//                    if (nUser != null) {
//                        userInitial = UserData(
//                            uid = user.uid,
//                            nickName = user.displayName.orEmpty(),
//                            email = user.email.orEmpty(),
//                            lastName = nUser.lastName,
//                            location = nUser.location,
//                            phone = nUser.phone,
//                            profileImageUrl = nUser.profileImageUrl
//
//                        )
//                    }
//                    userCommon(userInitial)
//
//                }
//
//            }
//        }
//        return userInitial
//    }

    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser?> {
        try {
            val response = auth.signInWithEmailAndPassword(email, password).await()
            return Result.Success(auth.currentUser)
        } catch (e: Exception) {
            return Result.Error(e)

        }
    }

    suspend fun registerWithEmail(
        nickName: String,
        email: String,
        password: String
    ): Result<UserData?> {
        val createResponse = createUserWithEmail(email, password)
        when (createResponse) {
            is Result.Success<*> -> signInWithEmail(email, password).also { signinResult ->
                when (signinResult) {
                    is Result.Success<*> -> {
                        val currentUser = signinResult.data
                        if (currentUser != null && currentUser is FirebaseUser) {
                            changeProfile(currentUser, nickName)
                            val newUser =
                                UserData(uid = currentUser.uid, nickName = nickName, email = email)
                            try {
                                repository.createUser(newUser)
                                userCommon(newUser)
                                return Result.Success(newUser)
                            } catch (e: java.lang.Exception) {
                                return Result.Error(e)
                            }
                        } else {
                            return Result.Success(null)
                        }

                    }
                    is Result.Error -> {
                        return Result.Error(signinResult.exception)
                    }
                    is Result.Canceled -> {
                        return Result.Canceled(signinResult.exception)
                    }
                }
            }
            is Result.Error -> {
                return Result.Error(createResponse.exception)
            }
            is Result.Canceled -> {
                return Result.Canceled(createResponse.exception)
            }
        }
    }

    suspend fun signInWithGoogle(acct: GoogleSignInAccount): Result<FirebaseUser?> {
        try {
            val response = auth.signInWithCredential(
                GoogleAuthProvider.getCredential(acct.idToken, null)
            ).await()
            return Result.Success(auth.currentUser)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun registerWithGoogle(acct: GoogleSignInAccount): Result<UserData?> {
        val createResponse = createUserWithGoogle(acct)
        when (createResponse) {
            is Result.Success<*> -> signInWithGoogle(acct).also { signinResult ->
                when (signinResult) {
                    is Result.Success<*> -> {
                        val currentUser = signinResult.data
                        if (currentUser != null && currentUser is FirebaseUser) {
                            changeProfile(currentUser, currentUser.displayName!!)
                            val newUser = UserData(
                                uid = currentUser.uid,
                                nickName = currentUser.displayName!!,
                                email = currentUser.email!!
                            )
//                            userCommon(newUser)
                            try {
                                repository.createUser(newUser)
                                userCommon(newUser)
                                return Result.Success(newUser)
                            } catch (e: java.lang.Exception) {
                                return Result.Error(e)
                            }
                        } else {
                            return Result.Success(null)
                        }
                    }
                    is Result.Error -> {
                        return Result.Error(signinResult.exception)
                    }
                    is Result.Canceled -> {
                        return Result.Canceled(signinResult.exception)
                    }
                }
            }
            is Result.Error -> {
                return Result.Error(createResponse.exception)
            }
            is Result.Canceled -> {
                return Result.Canceled(createResponse.exception)
            }
        }
    }

    suspend fun changeProfile(currentUser: FirebaseUser, nickName: String) {
        val request = userProfileChangeRequest {
            displayName = nickName
        }
        currentUser.updateProfile(request).await()
    }

    suspend fun createUserWithEmail(email: String, password: String): Result<AuthResult?> {
        return try {
            val data = auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            return Result.Success(data)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun createUserWithGoogle(acct: GoogleSignInAccount): Result<AuthResult?> {
        return try {
            val data = auth
                .signInWithCredential(GoogleAuthProvider.getCredential(acct.idToken, null))
                .await()
            return Result.Success(data)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    fun logout() {
        auth.signOut()
        Auth.GoogleSignInApi.signOut(fbase.provideGoogleClient.asGoogleApiClient()).setResultCallback {}
    }

    suspend fun loginUserByEmail(email: String, password: String): Result<FirebaseUser?> {
        try {
            val response = auth.signInWithEmailAndPassword(email, password).await()
            return Result.Success(auth.currentUser)
        } catch (e: Exception) {
            return Result.Error(e)

        }
    }

    suspend fun loginUserByGoogle(acct: GoogleSignInAccount): Result<FirebaseUser?> {
        try {
            val response = auth
                .signInWithCredential(GoogleAuthProvider.getCredential(acct.idToken, null))
                .await()
            googleProfile(response)
            return Result.Success(auth.currentUser)
        } catch (e: Exception) {
            return Result.Error(e)

        }
    }

    private suspend fun googleProfile(response: AuthResult) {
        val currentUser = response.user
        if (currentUser != null) {



            repository.createUser(userFromDB(response, currentUser))
            userCommon(userFromDB(response, currentUser))



        }
    }

    private fun userFromDB(
        response: AuthResult,
        currentUser: FirebaseUser
    ) : UserData {
        var newUser = UserData()
        response.user!!.uid.let {
            db.collection("users").document(it)
                .get()
                .addOnSuccessListener { link ->

                    val user = link.toObject<UserData?>()

                    newUser =
                        currentUser.let { rrr ->
                            user?.let {
                                UserData(
                                    uid = rrr.uid,
                                    nickName = rrr.displayName.toString(),
                                    email = rrr.email.toString(),
                                    phone = rrr.phoneNumber,
                                    location = user.location,
                                    profileImageUrl = user.profileImageUrl
                                )
                            }!!
                        }

                }
        }
        return newUser
    }

    fun userCommon(userData: UserData): UserData {
        USER = userData
        return USER as UserData
    }

    suspend fun sendPasswordResetEmail(emailAddress: String): Result<FirebaseUser?> {
        try {
            val response = auth
                .sendPasswordResetEmail(emailAddress)
                .await()
            return Result.Success(auth.currentUser)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun updatePassword(newPassword: String): Result<FirebaseUser?> {
        try {
            val user = auth.currentUser
            if (user != null) {
                user
                    .updatePassword(newPassword)
                    .await()
            }
            return Result.Success(auth.currentUser)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}