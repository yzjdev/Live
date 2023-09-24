package app.live.droid.logic.model

import com.drake.brv.item.ItemExpand

open class CategoryChild(
    override var itemExpand: Boolean = false,
    override var itemGroupPosition: Int = 0
) : ItemExpand {
    override fun getItemSublist(): List<Any?>? = null
}