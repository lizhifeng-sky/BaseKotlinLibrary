package android.com.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerAdapter : RecyclerView.Adapter<BaseRecyclerViewHolder<*>> {
    private var dataList: MutableList<BaseType<*>>? = null
    private var viewHolderFactory: ViewHolderFactory? = null
    private var context: Context? = null

    constructor(context: Context, dataList: MutableList<BaseType<*>>) {
        this.dataList = dataList
        this.context = context
    }

    constructor(context: Context) {
        this.context = context
        if (dataList == null) {
            dataList = mutableListOf()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return dataList?.get(position)?.getType()!!
    }

    fun createViewHolderFactory(viewHolderFactory: ViewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<*>{
        val itemView = LayoutInflater.from(context).inflate(viewType, parent, false)
        return viewHolderFactory!!.getViewHolder(viewType, itemView)
    }

    override fun getItemCount(): Int {
        return if (dataList != null) {
            dataList!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<*>, position: Int) {
        holder.setAdapter(this)
        holder.setItemViewData(context!!, dataList!![position].t)
    }

    fun getDataList(): MutableList<BaseType<*>>?{
        return dataList
    }

    fun clear() {
        dataList?.clear()
    }

    /*
     * 添加
     * */
    fun <T> singleTypeLoad(list: List<T>?, layoutId: Int) {
        if (list != null) {
            for (i in list.indices) {
                if (list[i] != null) {
                    dataList?.add(BaseType.addType(layoutId, list[i]))
                } else {
                    return
                }
            }
            notifyDataSetChanged()
        }
    }


    /*
     * 添加
     * */
    fun multiTypeLoad(list: List<BaseType<*>>?) {
        if (list != null) {
            dataList?.addAll(list)
            notifyDataSetChanged()
        }
    }

    //不能 使用刷新框架时 才使用
    fun showLoading(layoutId: Int) {
        dataList?.add(BaseType.addType(layoutId, layoutId))
        notifyDataSetChanged()
    }

    /*
     * 清除 添加
     * */
    fun <T> singleTypeRefresh(list: List<T>?, layoutId: Int) {
        if (list != null) {
            dataList?.clear()
            for (i in list.indices) {
                dataList?.add(BaseType.addType(layoutId, list[i]))
            }
            notifyDataSetChanged()
        }
    }

    /*
     * 清除 添加
     * */
    fun multiTypeRefresh(list: List<BaseType<*>>?) {
        if (list != null && list.size > 0) {
            dataList?.clear()
            dataList?.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun showEmptyView(layoutId: Int) {
        dataList?.clear()
        dataList?.add(BaseType.addType(layoutId, "empty"))
        notifyDataSetChanged()
    }
}