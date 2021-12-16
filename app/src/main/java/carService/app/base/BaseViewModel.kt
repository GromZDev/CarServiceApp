package carService.app.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.koin.core.component.KoinComponent

open class BaseViewModel(app: Application) : AndroidViewModel(app), KoinComponent {

    /**
     * хранит состояние загрузки данных. Нужен для управления отображением прогресс бара. Тип: LiveData<Boolean>.
     */
    var progressData: MutableLiveData<Boolean> = MutableLiveData()

    /**
     *     сеттер
     */
    protected fun updateProgress(progress: Boolean) {
        this.progressData.value = progress
    }
}