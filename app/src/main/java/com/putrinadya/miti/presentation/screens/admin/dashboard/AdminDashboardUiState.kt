package com.putrinadya.miti.presentation.screens.admin.dashboard

import com.putrinadya.miti.domain.model.Aspirations
import com.putrinadya.miti.domain.model.Event

data class AdminDashboardUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val events: List<Event> = emptyList(),
    val totalEvents: Int = 0,
    val activeParticipants: Int = 0,
    val newAspirations: Int = 0,
    val aspirationsList: List<Aspirations> = emptyList(),
    val currentSubScreen: String = "dashboard",
    val showCreateEventDialog: Boolean = false,
    val showEditEventDialog: Boolean = false,
    val selectedEventForEdit: Event? = null,
    val totalStudents: Int = 0,
    val showRegisterUserDialog: Boolean = false,
    val isRegistering: Boolean = false,

    // TAMBAHKAN VARIABEL PROFIL DINAMIS INI:
    val adminName: String = "Admin",
    val adminEmail: String = "",
    val adminNip: String = "",
    val adminTitle: String = "",
    val adminDepartment: String = "",
    val adminPhotoUrl: String = ""
)