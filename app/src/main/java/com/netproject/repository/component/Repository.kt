package com.netproject.repository.component

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

inline fun <T : Any> Observable<T>.arashi(
    crossinline success: (T) -> Unit = {}, crossinline error: () -> Unit = {}
): Disposable = compose { it }.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        {
            success(it)
        },
        {
            error()
        }
    )
