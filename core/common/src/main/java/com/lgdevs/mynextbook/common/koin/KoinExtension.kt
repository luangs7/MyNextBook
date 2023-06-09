package com.lgdevs.mynextbook.common.koin

import android.content.ComponentCallbacks
import org.koin.android.ext.android.get
import org.koin.core.error.NoBeanDefFoundException
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> ComponentCallbacks.injectOrNullable(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
) = try {
    get<T>(qualifier, parameters)
} catch (e: NoBeanDefFoundException) {
    null
}
