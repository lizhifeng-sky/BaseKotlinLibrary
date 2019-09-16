package android.com.base.extension

import java.math.BigDecimal


/**
 * Created by lizhifeng on 2018/12/7.
 */
fun <T> T.isNull(): Boolean {
    this?.let {
        return true
    }
    return false
}

fun String?.isStringNull(default: String = ""): String {
    this?.let {
        return this
    }
    return default
}
fun Int?.isIntNull(default: Int = 0): Int {
    this?.let {
        return this
    }
    return default
}

fun BigDecimal?.isBigDecimalNull(default: String = ""): BigDecimal {
    this?.let {
        return this
    }
    return BigDecimal.ZERO
}

fun <T> T.isNull(notNullBlock: () -> Unit,
                 nullBlock: () -> Unit) {
    if (this == null) {
        nullBlock()
    } else {
        notNullBlock()
    }
}

fun <T> T.isNull(notNullBlock: () -> Unit) {
    if (this != null) {
        notNullBlock()
    }
}

