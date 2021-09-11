package com.nedaluof.qurany.data.repoImpl

import android.content.Context
import com.nedaluof.qurany.domain.repositories.SurasRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * Created by nedaluof on 12/11/2020.
 */
class SurasRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : SurasRepository {
    override fun checkIfSuraExist(subPath: String) =
        File(context.getExternalFilesDir(null).toString() + subPath).exists()
}
