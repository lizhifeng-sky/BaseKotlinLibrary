package android.com.base.adapter

abstract class BaseType<T> : ViewHolderTypeInterface {
    var t: T? = null

    override fun getType(): Int {
        return getLayoutId()
    }

    abstract fun getLayoutId(): Int

    companion object {
        fun <T> addType(layoutId: Int, t: T): BaseType<T> {
            val baseType = object : BaseType<T>() {
                override fun getLayoutId(): Int {
                    return layoutId
                }
            }
            baseType.t = t
            return baseType
        }
    }

}