package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.AspirationRepository
import com.putrinadya.miti.domain.repository.EventRepository
import com.putrinadya.miti.domain.repository.AuthRepository
import com.putrinadya.miti.domain.usecase.admin.AddEventUseCase
import com.putrinadya.miti.domain.usecase.admin.DeleteAllEventsUseCase
import com.putrinadya.miti.domain.usecase.admin.DeleteEventUseCase
import com.putrinadya.miti.domain.usecase.admin.UpdateEventUseCase
import com.putrinadya.miti.domain.usecase.event.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val repository: EventRepository,
    private val authRepository: AuthRepository,
    private val aspirationRepository: AspirationRepository,
    private val getEventsUseCase: GetEventsUseCase,
    private val addEventUseCase: AddEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val deleteAllEventsUseCase: DeleteAllEventsUseCase,
    private val updateEventUseCase: UpdateEventUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AdminDashboardUiState())
        private set

    var eventsList = mutableStateListOf<Event>()
        private set

    init {
        observeEvents()
        loadAdminProfile() // Memuat profil admin saat pertama kali ViewModel dibuat
        observeStudentCount()
        observeAspirations()
    }

    private fun observeEvents() {
        // Mendengarkan total partisipan aktif secara real-time dengan proteksi try-catch
        viewModelScope.launch {
            try {
                repository.getTotalParticipants().collect { participantCount ->
                    uiState = uiState.copy(activeParticipants = participantCount)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Mendengarkan daftar event secara real-time
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                getEventsUseCase().collect { fetchedEvents ->
                    eventsList.clear()
                    eventsList.addAll(fetchedEvents)
                    uiState = uiState.copy(
                        isLoading = false,
                        totalEvents = eventsList.size
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    // Mendengarkan jumlah mahasiswa terdaftar secara real-time dari Firestore
    private fun observeStudentCount() {
        viewModelScope.launch {
            try {
                authRepository.getStudentCount().collect { count ->
                    uiState = uiState.copy(totalStudents = count)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun observeAspirations() {
        viewModelScope.launch {
            try {
                aspirationRepository.getAspirationCount().collect { count ->
                    uiState = uiState.copy(newAspirations = count)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        viewModelScope.launch {
            try {
                aspirationRepository.getAllAspirations().collect { list ->
                    uiState = uiState.copy(aspirationsList = list)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Membaca profil admin asli dari Firestore Database secara dinamis
    private fun loadAdminProfile() {
        viewModelScope.launch {
            authRepository.getFullCurrentUser()?.let { user ->
                uiState = uiState.copy(
                    adminName = user.name,
                    adminEmail = user.email,
                    adminNip = user.nip,
                    adminTitle = user.title,
                    adminDepartment = user.department
                )
            }
        }
    }

    fun onShowRegisterDialog(show: Boolean) {
        uiState = uiState.copy(showRegisterUserDialog = show)
    }

    // Fungsi pendaftaran mahasiswa baru yang bersih dan aman menggunakan operator invoke (.onSuccess)
    fun registerNewStudent(name: String, email: String, pass: String, nim: String) {
        if (name.isBlank() || email.isBlank() || pass.isBlank() || nim.isBlank()) {
            uiState = uiState.copy(error = "Mohon Isi Semua Kolom")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isRegistering = true, error = null)
            authRepository.registerStudent(name, email, pass, nim)
                .onSuccess {
                    uiState = uiState.copy(
                        isRegistering = false,
                        showRegisterUserDialog = false // Tutup dialog setelah sukses
                    )
                    // Statistik total mahasiswa akan otomatis terupdate via observeStudentCount() secara real-time
                }
                .onFailure { exception ->
                    uiState = uiState.copy(
                        isRegistering = false,
                        error = exception.message ?: "Gagal mendaftarkan mahasiswa"
                    )
                }
        }
    }

    fun createNewEvent(event: Event) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            // Memanggil Use Case baru dengan operator invoke
            addEventUseCase(event).onSuccess {
                uiState = uiState.copy(
                    isLoading = false,
                    showCreateEventDialog = false // Tutup dialog setelah sukses
                )
            }.onFailure { exception ->
                uiState = uiState.copy(
                    isLoading = false,
                    error = exception.message ?: "Gagal membuat event baru"
                )
            }
        }
    }

    fun onEditEventClick(event: Event) {
        uiState = uiState.copy(
            selectedEventForEdit = event,
            showEditEventDialog = true
        )
    }

    fun updateEvent(newEvent: Event) {
        val oldEvent = uiState.selectedEventForEdit
        if (oldEvent != null) {
            viewModelScope.launch {
                uiState = uiState.copy(isLoading = true, error = null)
                val eventToUpdate = newEvent.copy(id = oldEvent.id)

                updateEventUseCase(eventToUpdate).onSuccess {
                    uiState = uiState.copy(
                        isLoading = false,
                        showEditEventDialog = false,
                        selectedEventForEdit = null
                    )
                }.onFailure { exception ->
                    uiState = uiState.copy(
                        isLoading = false,
                        error = exception.message ?: "Gagal memperbarui event"
                    )
                }
            }
        }
    }

    fun removeEvent(event: Event) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            deleteEventUseCase(event).onSuccess {
                uiState = uiState.copy(isLoading = false)
            }.onFailure { exception ->
                uiState = uiState.copy(
                    isLoading = false,
                    error = exception.message ?: "Gagal menghapus event"
                )
            }
        }
    }

    fun clearAllEvents() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            deleteAllEventsUseCase().onSuccess {
                uiState = uiState.copy(isLoading = false)
            }.onFailure {
                uiState = uiState.copy(isLoading = false, error = it.message)
            }
        }
    }

    fun onShowCreateEventDialog(show: Boolean) {
        uiState = uiState.copy(showCreateEventDialog = show)
    }

    fun onCancelEdit() {
        uiState = uiState.copy(showEditEventDialog = false)
    }

    fun onNavigateToSubScreen(screen: String) {
        uiState = uiState.copy(currentSubScreen = screen)
    }

    fun logout() {
        authRepository.logout()
    }

    fun updateAdminProfile(name: String, nip: String, email: String, photoUrl: String) {
        viewModelScope.launch {
            try {
                // Ambil UID admin yang sedang login
                val uid = authRepository.getFullCurrentUser()?.uid ?: return@launch
                val updates = hashMapOf(
                    "name" to name,
                    "nip" to nip,
                    "email" to email,
                    "photoUrl" to photoUrl // Simpan link foto baru ke Firestore
                )

                // Update dokumen di Firestore
                com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .update(updates as Map<String, Any>)
                    .await()

                // Perbarui uiState lokal agar layar dashboard langsung terupdate secara real-time
                uiState = uiState.copy(
                    adminName = name,
                    adminEmail = email,
                    adminNip = nip,
                    adminPhotoUrl = photoUrl
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}