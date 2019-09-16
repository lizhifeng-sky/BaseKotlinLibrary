package android.com.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        setContentView(R.layout.activity_dialog)
    }

}
