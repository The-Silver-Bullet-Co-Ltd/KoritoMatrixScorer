package nz.co.silverbullet.koritomatrixscorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nz.co.silverbullet.koritomatrixscorer.repository.Repository

class Activity1ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Activity1ViewModel(repository) as T
    }
}