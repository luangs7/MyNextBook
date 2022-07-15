package com.lgdevs.mynextbook.common.helper

import android.content.Context
import android.content.Intent
import com.lgdevs.mynextbook.common.R

fun shareIntent(context: Context, message: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            context.getString(R.string.share_message, message)
        )
        type = context.getString(R.string.intent_type)
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}