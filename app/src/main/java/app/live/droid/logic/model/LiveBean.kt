package app.live.droid.logic.model

import java.io.Serializable

data class LiveBean(
    val roomId: String,
    val roomUrl: String,
    val name: String,
    val title: String,
    val gameName: String,
    val num: String,
    val avatar: String,
    val coverUrl: String,
    var stream: StreamBean?,
    var hasMore:Boolean = false
) : Serializable