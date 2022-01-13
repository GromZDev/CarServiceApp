package carService.app.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

open class BaseActivity(layout: Int) : AppCompatActivity(), KoinComponent {

    protected fun doInScope(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        action: suspend () -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(state) {
                action()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}