package app.live.droid.logic.model

data class StreamBean(val urls:List<String>, val rate:Int,  val rates:List<Rate>){
    fun toNewString(): String {
        val sb = StringBuilder()
        urls.forEach { item ->
            sb.append(item).append("\n")
        }
        rates.forEach { item ->
            sb.append(item).append("\n")
        }
        return sb.toString()
    }
}

data class Rate(val name:String, val rate:Int)

