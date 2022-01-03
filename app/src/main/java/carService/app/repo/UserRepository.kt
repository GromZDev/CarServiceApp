package carService.app.repo

import carService.app.data.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepository : KoinComponent {

    companion object {
        val instance = UserRepository()
    }

    private val firestore by inject<FirebaseFirestore>()

    suspend fun createUser(user: UserData) {
        val collection =firestore.collection("users")
        val document = collection.document(user.uid)
        document.set(user).await()
    }
}