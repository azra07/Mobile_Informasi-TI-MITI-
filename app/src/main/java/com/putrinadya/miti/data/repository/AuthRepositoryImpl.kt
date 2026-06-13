package com.putrinadya.miti.data.repository

import android.content.Context // MENAMBAHKAN IMPORT CONTEXT
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.putrinadya.miti.domain.model.User
import com.putrinadya.miti.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : AuthRepository {

    override fun login(email: String, password: String): Flow<Result<User>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: ""
                Log.d("MITI_DEBUG", "UID asli dari Auth adalah: $uid")

                firestore.collection("users").document(uid).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val user = User(
                                uid = uid,
                                name = document.getString("name") ?: "",
                                email = document.getString("email") ?: "",
                                role = document.getString("role") ?: "mahasiswa",
                                nim = document.getString("nim") ?: ""
                            )
                            trySend(Result.success(user))
                        } else {
                            trySend(Result.failure(Exception("Data pengguna tidak ditemukan di database.")))
                        }
                        close()
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
                Log.d("MITI_DEBUG", "UID asli dari Auth adalah: $uid")

                val userData = hashMapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to email,
                    "role" to role,
                    "nim" to if (role == "mahasiswa") nim else ""
                )
                firestore.collection("users").document(uid).set(userData)
                    .addOnSuccessListener {
                        trySend(Result.success(Unit))
                        close()
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

    // Mengubah fungsi registerStudent menjadi suspend yang sangat bersih dan aman
    override suspend fun registerStudent(
        name: String,
        email: String,
        pass: String,
        nim: String
    ): Result<Unit> = try {
        val secondaryAppName = "MitiSecondaryApp"
        val options = auth.app.options

        // Inisialisasi Firebase sekunder sementara agar tidak memutus sesi login Admin
        val secondaryApp = try {
            FirebaseApp.getInstance(secondaryAppName)
        } catch (e: Exception) {
            FirebaseApp.initializeApp(context, options, secondaryAppName)
        }

        val secondaryAuth = Firebase.auth(secondaryApp)

        // 1. Buat User di Firebase Auth menggunakan instance sekunder
        val authResult = secondaryAuth.createUserWithEmailAndPassword(email, pass).await()
        val uid = authResult.user?.uid ?: throw Exception("Gagal mendapatkan UID")

        // 2. Simpan Detail ke Firestore dengan role "mahasiswa"
        val userData = hashMapOf(
            "uid" to uid,
            "name" to name,
            "email" to email,
            "role" to "mahasiswa", // Set otomatis sebagai mahasiswa
            "nim" to nim
        )
        firestore.collection("users").document(uid).set(userData).await()

        // 3. Logout dari secondary app & hapus instancenya agar bersih
        secondaryAuth.signOut()
        secondaryApp.delete()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateUserProfile(name: String, nim: String, email: String): Result<Unit> = try {
        val uid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val updates = hashMapOf(
            "name" to name,
            "nim" to nim,
            "email" to email
        )
        firestore.collection("users").document(uid).update(updates as Map<String, Any>).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return if (firebaseUser != null) {
            User(firebaseUser.uid, firebaseUser.displayName ?: "", firebaseUser.email ?: "", "mahasiswa")
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
                role = document.getString("role") ?: "mahasiswa",
                nim = document.getString("nim") ?: "",
                nip = document.getString("nip") ?: "",
                title = document.getString("title") ?: "",
                department = document.getString("department") ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun getStudentCount(): Flow<Int> = callbackFlow {
        // Menghitung mahasiswa secara dinamis berdasarkan role "mahasiswa" secara real-time
        val subscription = firestore.collection("users")
            .whereEqualTo("role", "mahasiswa")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    trySend(snapshot.size())
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun sendPasswordResetEmail(email: String): Flow<Result<Unit>> = callbackFlow {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                trySend(Result.success(Unit))
                close()
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
                close()
            }
        awaitClose()
    }

    override fun logout() {
        auth.signOut()
    }
}