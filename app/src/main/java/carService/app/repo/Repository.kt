package carService.app.repo

import carService.app.data.model.UserData

class Repository(private val userRepository: UserRepository) {

    suspend fun createUser(user: UserData) =
        userRepository.createUser(user)
}