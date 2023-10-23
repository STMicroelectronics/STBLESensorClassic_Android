package com.st.blesensor.cloud.AwsIot

import android.net.Uri
import androidx.lifecycle.ViewModel

class AWsConfigViewModel : ViewModel() {

    //location where the certificate file is
    private var mCertificateFile: Uri? = null

    //location where the private key file is
    private var mPrivateKeyFile: Uri? = null

    fun setCertificate(file: Uri?) {
        mCertificateFile = file
    }

    fun getCerticate(): Uri? { return mCertificateFile}

    fun setKey(file: Uri?) {
        mPrivateKeyFile = file
    }

    fun getKey(): Uri? { return mPrivateKeyFile}
}
