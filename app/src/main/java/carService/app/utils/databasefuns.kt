package carService.app.utils

import carService.app.data.model.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

/**
 * Функция преобразовывает полученые данные из Firebase в модель UserData
  */

fun DataSnapshot.getUserData(): UserData =
    this.getValue(UserData::class.java) ?: UserData()

fun DatabaseReference.observeValue(): Flow<DataSnapshot?> =
    callbackFlow {
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot)
            }
        }
        addValueEventListener(listener)
        awaitClose { removeEventListener(listener) }
    }

inline fun<reified T> DatabaseReference.observeData(): Flow<T?> =
    observeValue().map {
        it?.getValue(T::class.java)
    }