package app.live.droid.utils

import org.mozilla.javascript.Context
import org.mozilla.javascript.Function


object ScriptUtil {
    fun eval(jsStr: String, functionName: String = "", vararg args: Any): String {
        val cx = Context.enter()
        cx.optimizationLevel = -1
        try {
            val scope = cx.initStandardObjects()
            cx.evaluateString(scope, jsStr, null, 1, null)
            val res = when (val o = scope.get(functionName, scope)) {
                is Function -> o.call(cx, scope, scope, args)
                else -> {}
            }
            return Context.toString(res)
        } finally {
            Context.exit()
        }

    }
}