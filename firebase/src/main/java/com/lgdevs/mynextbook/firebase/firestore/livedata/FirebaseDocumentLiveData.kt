package com.lgdevs.mynextbook.firebase.firestore.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class FirebaseDocumentLiveData(private val query: DocumentReference) : LiveData<DocumentSnapshot?>() {
    private val tag = "FirebaseQueryLiveData"

    private val listener = MyValueEventListener()
    private lateinit var registration: ListenerRegistration

    override fun onActive() {
        Log.d(tag, "onActive")
        registration = query.addSnapshotListener(listener)
    }

    override fun onInactive() {
        Log.d(tag, "onInactive")
        registration.remove()
    }

    private inner class MyValueEventListener : EventListener<DocumentSnapshot> {
        override fun onEvent(snapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
            if (e != null) {
                Log.w(tag, "listen:error", e)
                return
            }

            value = snapshot
        }
    }
}
