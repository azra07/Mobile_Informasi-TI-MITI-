package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import com.putrinadya.miti.domain.repository.AuthRepository
import com.putrinadya.miti.domain.usecase.admin.AddEventUseCase
import com.putrinadya.miti.domain.usecase.admin.DeleteEventUseCase
import com.putrinadya.miti.domain.usecase.admin.UpdateEventUseCase
import com.putrinadya.miti.domain.usecase.event.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val repository: EventRepository,
    private val authRepository: AuthRepository, // Suntikan Dependensi AuthRepository untuk memuat profil admin
    private val getEventsUseCase: GetEventsUseCase,
    private val addEventUseCase: AddEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AdminDashboardUiState())
        private set

    var eventsList = mutableStateListOf<Event>()
        private set

    init {
        observeEvents()
        loadAdminProfile() // Memuat profil admin saat pertama kali ViewModel dibuat
    }

    private fun observeEvents() {
        // Mendengarkan total partisipan aktif secara real-time
        viewModelScope.launch {
            repository.getTotalParticipants().collect { participantCount ->
                uiState = uiState.copy(activeParticipants = participantCount)
            }
        }

        // Mendengarkan daftar event secara real-time (Otomatis memperbarui daftar & statistik total event)
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            getEventsUseCase().collect { fetchedEvents ->
                eventsList.clear()
                eventsList.addAll(fetchedEvents)
                uiState = uiState.copy(
                    isLoading = false,
                    totalEvents = eventsList.size
                )
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
}
