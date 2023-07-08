package com.lgdevs.mynextbook.firebase.firestore.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class FirebaseQueryLiveData(val query: Query) : LiveData<QuerySnapshot?>() {
    private val LOG_TAG = "FirebaseQueryLiveData"

    private val listener = MyValueEventListener()
    private lateinit var registration: ListenerRegistration

    override fun onActive() {
        Log.d(LOG_TAG, "onActive")
        registration = query.addSnapshotListener(listener)
    }

    override fun onInactive() {
        Log.d(LOG_TAG, "onInactive")
        registration.remove()
    }

    private inner class MyValueEventListener : EventListener<QuerySnapshot> {
        override fun onEvent(snapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
            if (e != null) {
                Log.w(LOG_TAG, "listen:error", e)
                return
            }

            value = snapshots
        }
    }
}
