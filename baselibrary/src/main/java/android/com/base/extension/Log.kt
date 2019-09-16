package android.com.base.extension

import android.util.Log
/**
 * Created by lizhifeng on 2018/12/7.
 */

fun Any.log(msg: String) {
    val throwable = Throwable(msg)
    val stackTrace = throwable.stackTrace
    val traceElement = stackTrace.get(1)
    Log.e(
        "lzf_" + "(" + traceElement.fileName + ":" + traceElement.lineNumber + ")",
        throwable.message
    )
}

