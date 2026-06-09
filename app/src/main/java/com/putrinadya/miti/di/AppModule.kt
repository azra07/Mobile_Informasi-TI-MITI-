package com.putrinadya.miti.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.putrinadya.miti.data.repository.AuthRepositoryImpl
import com.putrinadya.miti.data.repository.EventRepositoryImpl
import com.putrinadya.miti.domain.repository.AuthRepository
import com.putrinadya.miti.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // 1. Menyediakan Firebase Auth
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    // 2. Menyediakan Firebase Firestore
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    // 3. Menyediakan Room Database (Kriteria Wajib Local DB)
    /*
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MitiDatabase {
        return Room.databaseBuilder(
            context,
            MitiDatabase::class.java,
            "miti_db"
        ).build()
    }
    */

    // 4. Menghubungkan Interface Repository dengan Implementasinya (Clean Architecture)
    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        firestore: FirebaseFirestore
        // api: NewsApiService (Nanti tambahkan di sini setelah Retrofit siap)
    ): EventRepository {
        return EventRepositoryImpl(firestore)
    }
}