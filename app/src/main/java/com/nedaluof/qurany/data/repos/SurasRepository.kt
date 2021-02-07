package com.nedaluof.qurany.data.repos

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * Created by nedaluof on 12/11/2020.
 */
class SurasRepository @Inject constructor(
        @ApplicationContext
        private val context: Context,
) {
    fun checkIfSuraExist(subPath: String) =
            File(context.getExternalFilesDir(null).toString() + subPath).exists()
}
