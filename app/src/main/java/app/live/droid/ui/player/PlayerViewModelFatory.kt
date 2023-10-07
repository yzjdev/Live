package app.live.droid.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.live.droid.parser.LiveParser

class PlayerViewModelFatory(val parser: LiveParser) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(parser) as T
    }
}