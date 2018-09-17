package com.itgosolutions.kotlinchat.ui.register

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.ui.common.*
import com.itgosolutions.kotlinchat.ui.home.HomeActivity
import com.itgosolutions.kotlinchat.ui.login.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import javax.inject.Inject

class RegisterActivity : BaseActivity(), RegisterContract.View {

    companion object {

        private val GALLERY_PERMISSION = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private const val GALLERY_PERMISSION_REQUEST_CODE = 101

        fun start(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: RegisterContract.Presenter
    private var pickedImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getActivityComponent().inject(this)

        setSystemBarColor(R.color.blue_grey_900)

        registerButton.setOnClickListener { registerUser() }
        loginTextView.setOnClickListener { startLoginScreenAndFinish() }
        avatarImageView.setOnClickListener { tryOpenGallery() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
            if (canOpenGallery()) {
                openGallery()
            } else if (!shouldShowGalleryRational()) {
                showLongToast("Please give permission from Settings")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
                if (imageFile != null) {
                    Picasso.get().load(imageFile).into(avatarImageView)
                    pickedImageFile = imageFile
                }
            }
        })
    }

    override fun initView() {
        presenter.initView(this)
    }

    override fun destroyView() {
        presenter.destroyView()
    }

    override fun showLoading() {
        progress.visibility = VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = INVISIBLE
    }

    override fun enableRegisterButton() {
        registerButton.visibility = VISIBLE
    }

    override fun disableRegisterButton() {
        registerButton.visibility = INVISIBLE
    }

    override fun enableLoginInfoContainer() {
        loginInfoContainer.visibility = VISIBLE
    }

    override fun disableLoginInfoContainer() {
        loginInfoContainer.visibility = INVISIBLE
    }

    override fun showUsernameError(errorMessage: String) {
        showShortToast(errorMessage)
    }

    override fun showEmailError(errorMessage: String) {
        showShortToast(errorMessage)
    }

    override fun showPasswordError(errorMessage: String) {
        showShortToast(errorMessage)
    }

    override fun startHomeScreen() {
        HomeActivity.start(this)
        finish()
    }

    override fun showRegistrationError(errorMessage: String) {
        showShortToast(errorMessage)
    }

    private fun tryOpenGallery() {
        when {
            canOpenGallery() -> openGallery()
            shouldShowGalleryRational() -> {
                showLongToast("Should Give Permission")
                askPermissionForGallery()
            }
            else -> askPermissionForGallery()
        }
    }

    private fun askPermissionForGallery() {
        ActivityCompat.requestPermissions(this, netPermissions(GALLERY_PERMISSION), GALLERY_PERMISSION_REQUEST_CODE)
    }

    private fun openGallery() {
        EasyImage.openGallery(this, 0)
    }

    private fun startLoginScreenAndFinish() {
        LoginActivity.start(this)
        finish()
    }

    private fun registerUser() {
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        presenter.registerUser(username, email, password, pickedImageFile)
    }

    private fun shouldShowGalleryRational(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun canOpenGallery(): Boolean {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
