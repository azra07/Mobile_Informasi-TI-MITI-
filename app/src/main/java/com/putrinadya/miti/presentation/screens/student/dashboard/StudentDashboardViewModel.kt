package com.putrinadya.miti.presentation.screens.student.dashboard

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.model.Aspirations
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.AspirationRepository
import com.putrinadya.miti.domain.repository.EventRepository
import com.putrinadya.miti.domain.usecase.event.RegisterToEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.putrinadya.miti.R
import android.content.Context
import com.putrinadya.miti.R.string.failed_send_link
import com.putrinadya.miti.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import android.widget.Toast

@HiltViewModel
class StudentDashboardViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val registerToEventUseCase: RegisterToEventUseCase,
    private val authRepository: com.putrinadya.miti.domain.repository.AuthRepository,
    private val aspirationRepository: AspirationRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _allEvents = MutableStateFlow<List<Event>>(emptyList())
    val allEvents: StateFlow<List<Event>> = _allEvents.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    var selectedTab by mutableStateOf(0)
        private set

    var selectedEventForRegistration by mutableStateOf<Event?>(null)
        private set

    var passwordResetMessage by mutableStateOf<String?>(null)
        private set

    var showAspirationDialog by mutableStateOf(false)
        private set

    var aspirationStatusMessage by mutableStateOf<String?>(null)
        private set

    val registeredEvents = mutableStateListOf<Event>()

    var studentProfile by mutableStateOf<User?>(null)
        private set

    init {
        loadEventsFromRepository()
        loadStudentProfile()
        loadRegisteredEvents()
    }

    private fun loadRegisteredEvents() {
        viewModelScope.launch {
            try {
                val uid = authRepository.getFullCurrentUser()?.uid ?: return@launch

                // Mendengarkan sub-koleksi 'registered_events' milik user yang sedang login
                com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .collection("registered_events")
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            error.printStackTrace()
                            return@addSnapshotListener
                        }

                        if (snapshot != null) {
                            val list = snapshot.documents.mapNotNull { doc ->
                                doc.toObject(Event::class.java)
                            }
                            registeredEvents.clear()
                            registeredEvents.addAll(list) // Update list lokal secara otomatis
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadEventsFromRepository() {
        viewModelScope.launch {
            eventRepository.getEvents().collectLatest { list ->
                _allEvents.value = list
            }
        }
    }

    val filteredEvents: List<Event>
        get() = if (searchQuery.isEmpty()) {
            _allEvents.value
        } else {
            _allEvents.value.filter {
                it.title.contains(searchQuery, ignoreCase = true) || it.category.contains(searchQuery, ignoreCase = true)
            }
        }

    fun sendPasswordReset() {
        val userEmail = authRepository.getCurrentUser()?.email
        if (userEmail != null) {
            viewModelScope.launch {
                authRepository.sendPasswordResetEmail(userEmail).collect { result ->
                    result.onSuccess {
                        passwordResetMessage = context.getString(R.string.send_link)
                    }
                    result.onFailure { exception ->
                        passwordResetMessage = context.getString(failed_send_link) + (exception.message ?: "")
                    }
                }
            }
        }
    }

    fun clearPasswordMessage() {
        passwordResetMessage = null
    }

    fun loadStudentProfile() {
        viewModelScope.launch {
            studentProfile = authRepository.getFullCurrentUser()
        }
    }

    fun updateProfile(name: String, nim: String, email: String) {
        viewModelScope.launch {
            val result = authRepository.updateUserProfile(name, nim, email)
            if (result.isSuccess) {
                loadStudentProfile()
            }
        }
    }

    fun onShowAspirationDialog(show: Boolean) {
        showAspirationDialog = show
    }

    fun sendAspiration(content: String, category: String, isAnonymous: Boolean) {
        viewModelScope.launch {
            val user = authRepository.getFullCurrentUser()
            val aspiration = Aspirations(
                userId = user?.uid ?: "",
                userName = user?.name ?: "Mahasiswa",
                content = content,
                category = category,
                anonymous = isAnonymous
            )
            aspirationRepository.sendAspiration(aspiration).onSuccess {
                showAspirationDialog = false
                aspirationStatusMessage = context.getString(R.string.send_aspiration)
            }.onFailure { exception ->
                aspirationStatusMessage = context.getString(R.string.failed_send_aspiration) + (exception.message ?: "")
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
    }

    fun onTabSelected(tabIndex: Int) {
        selectedTab = tabIndex
    }

    fun onEventSelected(event: Event?) {
        selectedEventForRegistration = event
    }

    fun registerForEvent(event: Event) {
        // Validasi kuota penuh
        if (event.currentParticipants >= event.maxParticipants) {
            android.widget.Toast.makeText(context, "Maaf, kuota kegiatan ini sudah penuh!", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                val uid = authRepository.getFullCurrentUser()?.uid ?: return@launch

                // A. Simpan data event ke sub-koleksi milik user di Firestore agar tersimpan permanen
                com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .collection("registered_events")
                    .document(event.id)
                    .set(event)
                    .await()

                // B. Tambahkan (+1) kuota peserta di koleksi utama 'events'
                val updatedParticipants = event.currentParticipants + 1
                com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    .collection("events")
                    .document(event.id)
                    .update("currentParticipants", updatedParticipants)
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun unregisterForEvent(event: Event) {
        viewModelScope.launch {
            try {
                val uid = authRepository.getFullCurrentUser()?.uid ?: return@launch

                // A. Hapus data event dari sub-koleksi milik user di Firestore secara permanen
                com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .collection("registered_events")
                    .document(event.id)
                    .delete()
                    .await()

                // B. Kurangi (-1) kuota peserta di koleksi utama 'events'
                val updatedParticipants = (event.currentParticipants - 1).coerceAtLeast(0)
                com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    .collection("events")
                    .document(event.id)
                    .update("currentParticipants", updatedParticipants)
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun logout() {
        authRepository.logout()
    }
}