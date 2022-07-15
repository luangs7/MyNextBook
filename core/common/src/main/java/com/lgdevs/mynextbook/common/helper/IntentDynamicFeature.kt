package com.lgdevs.mynextbook.common.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

@SuppressLint("QueryPermissionsNeeded")
fun Intent.convertToSafeDynamicFeatureModuleIntent(context: Context) {
    //Get list of all intent handlers for this Intent. This should only be the actual activity we are looking for
    val options = context.packageManager.queryIntentActivities(this, PackageManager.MATCH_DEFAULT_ONLY)
    //Set the activity that supported the given intent
    setClassName(context.packageName, options[0].activityInfo.name)
}