package app.live.droid.ui.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.live.droid.parser.LiveParser

class RecommendViewModelFactory(private val liveParser: LiveParser) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecommendViewModel(liveParser) as T
    }
}