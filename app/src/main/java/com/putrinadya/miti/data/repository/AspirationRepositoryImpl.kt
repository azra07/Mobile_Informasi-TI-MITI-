package com.putrinadya.miti.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.putrinadya.miti.domain.model.Aspirations
import com.putrinadya.miti.domain.repository.AspirationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AspirationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AspirationRepository {
    override suspend fun sendAspiration(aspiration: Aspirations): Result<Unit> = try {
        firestore.collection("aspirations").add(aspiration).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getAllAspirations(): Flow<List<Aspirations>> = callbackFlow {
        val subscription = firestore.collection("aspirations")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(Aspirations::class.java)
                    trySend(list)
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun getAspirationCount(): Flow<Int> = callbackFlow {
        val subscription = firestore.collection("aspirations")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) trySend(snapshot.size())
            }
        awaitClose { subscription.remove() }
    }
}