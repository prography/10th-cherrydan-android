package com.hyunjung.core.domain.repository

import android.content.Context
import com.hyunjung.core.common.util.DataError
import com.hyunjung.core.common.util.Result
import com.hyunjung.core.model.LoginResult
import com.hyunjung.core.model.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(context: Context, socialType: SocialType): Flow<Result<LoginResult, DataError>>
}