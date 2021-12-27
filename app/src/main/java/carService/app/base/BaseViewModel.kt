package carService.app.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent

open class BaseViewModel(app: Application) : AndroidViewModel(app), KoinComponent {

    /**
     * хранит состояние загрузки данных. Нужен для управления отображением прогресс бара. Тип: LiveData<Boolean>.
     */
    var progressData: MutableLiveData<Boolean> = MutableLiveData()

    val error: MutableStateFlow<String> = MutableStateFlow("")
    val uiDispatcher = Dispatchers.Main.immediate
    val ioDispatcher = Dispatchers.IO
    val job = SupervisorJob()
    val modelScope = CoroutineScope(uiDispatcher + job)

    /**
     *     сеттер
     */
    protected fun updateProgress(progress: Boolean) {
        this.progressData.value = progress
    }
}