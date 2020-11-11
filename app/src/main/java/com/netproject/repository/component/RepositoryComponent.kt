package com.netproject.repository.component

import com.netproject.repository.api.ApiService
import com.netproject.repository.okhttp.OkHttpService

object RepositoryComponent {
    val api: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpService.getService()
    }
    val other: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpService.getOtherService()
    }
}
