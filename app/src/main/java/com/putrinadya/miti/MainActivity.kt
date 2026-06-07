//package com.putrinadya.miti
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.viewModels
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.ui.Modifier
//import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboard
//import com.putrinadya.miti.presentation.screens.student.dashboard.StudentViewModel
//import com.putrinadya.miti.ui.theme.MITITheme
//
//class MainActivity : ComponentActivity() {
//    // Menginisialisasi ViewModel menggunakan delegasi viewModels() kustom Android
//    private val studentViewModel: StudentViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MITITheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    // Menginjeksikan instance ViewModel yang sama ke dalam Dashboard Mahasiswa
//                    StudentDashboard(viewModel = studentViewModel)
//                }
//            }
//        }
//    }
//}