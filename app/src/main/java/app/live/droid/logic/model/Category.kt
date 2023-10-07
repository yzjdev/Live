package app.live.droid.logic.model

import androidx.databinding.BaseObservable
import com.drake.brv.item.ItemExpand


open class Category : ItemExpand, BaseObservable(){
    override var itemExpand: Boolean = false
        set(value) {
            field = value
            notifyChange()
        }


    override var itemGroupPosition = 0



    override fun getItemSublist(): List<Any?>? {
        return mutableListOf(CategoryChild(),CategoryChild(), CategoryChild(),CategoryChild(),CategoryChild(),CategoryChild(),CategoryChild(),CategoryChild())
    }


}