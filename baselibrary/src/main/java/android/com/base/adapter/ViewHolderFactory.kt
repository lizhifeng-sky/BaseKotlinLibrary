package android.com.base.adapter

import android.view.View

abstract class ViewHolderFactory {
    fun getViewHolder(viewType: Int, itemView: View): BaseRecyclerViewHolder<*> {
        val itemViewHolder = getItemViewHolder(viewType, itemView)
        if (itemViewHolder == null) {
            itemView.visibility=View.GONE
            return DefaultViewHolder(itemView)
        } else {
            return itemViewHolder
        }
    }
    abstract fun getItemViewHolder(viewType: Int, itemView: View): BaseRecyclerViewHolder<*>?
}