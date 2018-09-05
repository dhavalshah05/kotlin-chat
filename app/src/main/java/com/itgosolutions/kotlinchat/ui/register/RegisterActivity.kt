package com.itgosolutions.kotlinchat.ui.register

import android.os.Bundle
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.ui.common.BaseActivity

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        getActivityComponent().inject(this)
    }
}
