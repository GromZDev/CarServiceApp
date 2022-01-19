package carService.app.repo.personal

import carService.app.data.model.UserData
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class Repository(private val userRepository: UserRepository) {

    suspend fun createUser(user: UserData?) =
        user?.let { userRepository.createUser(it) }
}