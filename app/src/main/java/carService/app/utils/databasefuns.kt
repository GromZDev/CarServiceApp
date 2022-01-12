package carService.app.utils

import carService.app.data.model.UserData
import com.google.firebase.database.DataSnapshot

/**
 * Функция преобразовывает полученые данные из Firebase в модель UserData
  */

fun DataSnapshot.getUserData(): UserData =
    this.getValue(UserData::class.java) ?: UserData()