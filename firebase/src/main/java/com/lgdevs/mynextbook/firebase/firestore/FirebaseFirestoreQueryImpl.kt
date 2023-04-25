package com.lgdevs.mynextbook.firebase.firestore

import androidx.lifecycle.MediatorLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.lgdevs.mynextbook.cloudservices.firestore.CloudServicesRealTimeDatabase
import com.lgdevs.mynextbook.firebase.firestore.livedata.FirebaseDocumentLiveData
import com.lgdevs.mynextbook.firebase.firestore.livedata.FirebaseQueryLiveData
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseFirestoreQueryImpl : CloudServicesRealTimeDatabase {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun <T> whereEqualTo(
        collection: String,
        reference: String,
        value: String,
        objectType: Class<T>,
    ): MediatorLiveData<List<T>> {
        val mediatorLiveData = MediatorLiveData<List<T>>()

        val collectionReference = firestore.collection(collection)
        val firebaseQueryLiveData =
            FirebaseQueryLiveData(collectionReference.whereEqualTo(reference, value))

        mediatorLiveData.addSource(firebaseQueryLiveData) {
            mediatorLiveData.postValue(it?.toObjects(objectType))
        }

        return mediatorLiveData
    }

    override suspend fun <T> getDocumentById(
        collection: String,
        documentId: String,
        objectType: Class<T>,
    ): MediatorLiveData<T> {
        val mediatorLiveData = MediatorLiveData<T>()

        val collectionReference = firestore.collection(collection)
        val firebaseDocumentLiveData =
            FirebaseDocumentLiveData(collectionReference.document(documentId))

        mediatorLiveData.addSource(firebaseDocumentLiveData) {
            mediatorLiveData.postValue(it?.toObject(objectType))
        }

        return mediatorLiveData
    }

    override suspend fun saveDocument(collection: String, value: Any): String =
        suspendCoroutine {
            val collectionReference = firestore.collection(collection)
            collectionReference
                .add(value)
                .addOnSuccessListener { documentId -> it.resume(documentId.id) }
                .addOnFailureListener { exception -> it.resumeWithException(exception) }
        }
}
