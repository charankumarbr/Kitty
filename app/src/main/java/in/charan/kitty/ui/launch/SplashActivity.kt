package `in`.charan.kitty.ui.launch

import `in`.charan.kitty.BuildConfig
import `in`.charan.kitty.R
import `in`.charan.kitty.ui.home.HomeActivity
import `in`.charan.kitty.util.AppUtil
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {
        val tvAppVersion = findViewById<MaterialTextView>(R.id.asTvVersion)
        tvAppVersion.text = "v ${BuildConfig.VERSION_NAME}"

        Completable.timer(AppUtil.SPLASH_TIMER, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                navigateToHome()
            }, {
                navigateToHome()
            })

    }

    private fun navigateToHome() {
        if (!isFinishing) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}