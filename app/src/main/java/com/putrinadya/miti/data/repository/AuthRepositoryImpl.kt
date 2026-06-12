package com.putrinadya.miti.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.putrinadya.miti.domain.model.User
import com.putrinadya.miti.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun login(email: String, password: String): Flow<Result<User>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: ""
                android.util.Log.d("MITI_DEBUG", "UID asli dari Auth adalah: $uid")
                firestore.collection("users").document(uid).get()
                // Setelah login sukses, ambil data dari Firestore
                firestore.collection("users").document(uid).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val user = User(
                                uid = uid,
                                name = document.getString("name") ?: "",
                                email = document.getString("email") ?: "",
                                role = document.getString("role") ?: "student",
                                nim = document.getString("nim") ?: ""
                            )
                            trySend(Result.success(user))
                        } else {
                            // Jika dokumen tidak ditemukan di Firestore
                            trySend(Result.failure(Exception("Data pengguna tidak ditemukan di database.")))
                        }
                        close() // Tutup channel setelah data dikirim
                    }
                    .addOnFailureListener {
                        trySend(Result.failure(it))
                        close()
                    }
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
                close()
            }
        awaitClose()
    }

    override fun register(
        name: String,
        email: String,
        password: String,
        role: String,
        nim: String
    ): Flow<Result<Unit>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: ""
                android.util.Log.d("MITI_DEBUG", "UID asli dari Auth adalah: $uid")
                firestore.collection("users").document(uid).get()
                val userData = hashMapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to email,
                    "role" to role,
                    "nim" to if (role == "student") name.filter { it.isDigit() } else ""
                )
                // Simpan data user ke Firestore agar kita tahu dia Admin atau Mahasiswa
                firestore.collection("users").document(uid).set(userData)
                    .addOnSuccessListener { trySend(Result.success(Unit)) }
                    .addOnFailureListener { trySend(Result.failure(it)) }
            }
            .addOnFailureListener { trySend(Result.failure(it)) }
        awaitClose()
    }

    override fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return if (firebaseUser != null) {
            // Ini versi simpel, idealnya data ini diambil dari cache atau Firestore
            User(firebaseUser.uid, firebaseUser.displayName ?: "", firebaseUser.email ?: "", "student")
        } else null
    }

    override suspend fun getFullCurrentUser(): User? {
        val firebaseUser = auth.currentUser ?: return null
        return try {
            val document = firestore.collection("users").document(firebaseUser.uid).get().await()
            User(
                uid = firebaseUser.uid,
                name = document.getString("name") ?: "",
                email = document.getString("email") ?: "",
                role = document.getString("role") ?: "student",
                nim = document.getString("nim") ?: "",
                nip = document.getString("nip") ?: "",          // TAMBAHKAN INI
                title = document.getString("title") ?: "",      // TAMBAHKAN INI
                department = document.getString("department") ?: "" // TAMBAHKAN INI
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun logout() {
        auth.signOut()
    }
}