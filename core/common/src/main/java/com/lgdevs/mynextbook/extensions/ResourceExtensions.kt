package com.lgdevs.mynextbook.extensions

import com.lgdevs.mynextbook.common.base.ApiResult


fun <T> ApiResult<T>.isSuccess():Boolean = this is ApiResult.Success
fun <T> ApiResult<T>.isError():Boolean = this is ApiResult.Error
