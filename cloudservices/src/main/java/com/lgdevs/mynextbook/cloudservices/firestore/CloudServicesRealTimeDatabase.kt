package com.lgdevs.mynextbook.cloudservices.firestore

import androidx.lifecycle.MediatorLiveData

interface CloudServicesRealTimeDatabase {

    suspend fun <T> whereEqualTo(
        collection: String,
        reference: String,
        value: String,
        objectType: Class<T>,
    ): MediatorLiveData<List<T>>

    suspend fun <T> getDocumentById(
        collection: String,
        documentId: String,
        objectType: Class<T>,
    ): MediatorLiveData<T>

    suspend fun saveDocument(collection: String, value: Any): String
}
