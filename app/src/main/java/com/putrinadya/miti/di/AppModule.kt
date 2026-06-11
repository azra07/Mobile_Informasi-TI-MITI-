package com.putrinadya.miti.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.putrinadya.miti.data.local.MitiDatabase
import com.putrinadya.miti.data.local.dao.MitiDao
import com.putrinadya.miti.data.remote.NewsApiService
import com.putrinadya.miti.data.repository.NewsRepositoryImpl
import com.putrinadya.miti.data.repository.AuthRepositoryImpl
import com.putrinadya.miti.data.repository.EventRepositoryImpl
import com.putrinadya.miti.domain.repository.AuthRepository
import com.putrinadya.miti.domain.repository.EventRepository
import com.putrinadya.miti.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    // 3. Menyediakan Room Database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MitiDatabase {
        return Room.databaseBuilder(
            context,
            MitiDatabase::class.java,
            "miti_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMitiDao(db: MitiDatabase): MitiDao = db.dao

    // 4. Menghubungkan Interface Repository dengan Implementasinya
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
        firestore: FirebaseFirestore,
        dao: MitiDao
    ): EventRepository {
        return EventRepositoryImpl(firestore, dao)
    }

    @Provides
    @Singleton
    fun provideNewsApiService(): NewsApiService {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApiService, dao: MitiDao): NewsRepository {
        return NewsRepositoryImpl(api, dao)
    }
}