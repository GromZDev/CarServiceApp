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
            val newUser =
                currentUser.let {
                    UserData(uid = it.uid, nickName = it.displayName.toString(), email = it.email.toString())
                }
                repository.createUser(newUser)
                userCommon(newUser)
        }
    }


    fun userCommon(userData: UserData): UserData {
        USER = userData
        return USER as UserData
    }
}