package android.com.base.extension

import android.com.base.adapter.BaseRecyclerAdapter
import android.com.base.adapter.BaseRecyclerViewHolder
import android.com.base.adapter.ViewHolderFactory
import android.view.View
import java.lang.reflect.Method

fun BaseRecyclerAdapter.createViewHolderFactory(viewHolderBlock: (viewType: Int, itemView: View) -> BaseRecyclerViewHolder<*>?) {
    this.createViewHolderFactory(object : ViewHolderFactory() {
        override fun getItemViewHolder(viewType: Int, itemView: View): BaseRecyclerViewHolder<*>? {

            return viewHolderBlock(viewType, itemView)
        }
    })
}
fun Int.getViewHolder(vararg method:(holder:()->BaseRecyclerViewHolder<*>)->(()->BaseRecyclerViewHolder<*>)){
    for(item in method){

    }
}

fun Int.createViewHolder(holder:()->BaseRecyclerViewHolder<*>):()->BaseRecyclerViewHolder<*>{
    return holder
}
