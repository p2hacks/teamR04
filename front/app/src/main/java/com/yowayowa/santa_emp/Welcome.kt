package com.yowayowa.santa_emp

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.stephentuso.welcome.*

class Welcome : WelcomeActivity() {

    companion object{
        fun showIfNeeded(activity: Activity, savedInstanceState: Bundle?) {
            WelcomeHelper(activity, Welcome::class.java).show(savedInstanceState)
        }
        //強制
        fun showForcibly(activity: Activity) {
            WelcomeHelper(activity, Welcome::class.java).forceShow()
        }
    }

    override fun configuration(): WelcomeConfiguration {
        return WelcomeConfiguration.Builder(this)
            .defaultBackgroundColor(BackgroundColor(Color.RED))
            .page(TitlePage(R.drawable.ic_appicon, "Santa EMP System"))
            .page(BasicPage(
                R.drawable.ic_light,
                "サンタのためのEMP",
                "快適なプレゼント配りをあなたに")
                .background(BackgroundColor(Color.YELLOW))
                .headerColor(Color.BLACK)
                .descriptionColor(Color.BLACK))
            .page(BasicPage(
                android.R.drawable.ic_lock_power_off,
                "Santa emp はブレーカーを落とします",
                "これで身バレの心配は不要")
                .background(BackgroundColor(Color.GRAY))
            )
            .swipeToDismiss(true)
            .build()
    }
}
