package com.hyunjung.core.network.datasource

import android.content.Context
import com.hyunjung.core.common.util.DataError
import com.hyunjung.core.common.util.Result
import com.hyunjung.core.model.AuthTokens

interface SocialAuthDataSource {
    suspend fun login(context: Context): Result<AuthTokens, DataError>
}