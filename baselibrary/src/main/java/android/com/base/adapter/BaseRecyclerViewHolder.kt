package android.com.base.adapter

import android.com.base.extension.log
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var adapter: BaseRecyclerAdapter? = null

    fun <INPUT> setItemViewData(context: Context, model: INPUT) {
        try {
            findView()
            setViewData(context, model as T)
        } catch (e: ClassCastException) {
            log("类型转换异常")
        }
    }

    abstract fun findView()

    abstract fun setViewData(context: Context, model: T)

    fun setAdapter(adapter: BaseRecyclerAdapter) {
        this.adapter = adapter
    }

    inline fun <reified T> checkModelType(any: Any): Boolean {
        return any is T
    }

    fun <V : View> Int.find(): V {
        return itemView.findViewById(this)
    }
}