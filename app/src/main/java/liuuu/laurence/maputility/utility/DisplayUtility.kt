package liuuu.laurence.maputility.utility

import android.content.Context

// Reference: https://www.jianshu.com/p/ff67ba80fe36
object DisplayUtility {
    /**
     * px to dp
     *
     * @param pxValue
     * @param scale
     * @return
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * dp to px
     *
     * @param dipValue
     * @param scale
     * @return
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * px to sp
     *
     * @param pxValue
     * @param fontScale
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * sp to px
     *
     * @param spValue
     * @param fontScale
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}