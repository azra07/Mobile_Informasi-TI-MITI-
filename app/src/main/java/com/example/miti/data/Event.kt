package com.example.miti.data

import androidx.compose.ui.graphics.Color

data class Event(
    val title: String,
    val category: String,
    val dateMonth: String, // Contoh: "Apr"
    val dateDay: String,   // Contoh: "15"
    val time: String,      // Contoh: "10:00 AM"
    val location: String,
    val currentParticipants: Int,
    val maxParticipants: Int,
    val description: String,
    val categoryColor: Color
)

// Data dummy sesuai mockup yang Anda lampirkan
val dummyEvents = listOf(
    Event("UI/UX Design Workshop", "Workshop", "Apr", "15", "10:00 AM", "Tech Hub, Room 301", 42, 50, "Learn the fundamentals of user interface and experience design...", Color(0xFFC583FF)),
    Event("Annual Hackathon 2026", "Hackathon", "Apr", "20", "9:00 AM", "Innovation Center", 156, 200, "Join the biggest competitive hackathon of the year...", Color(0xFF00E676)),
    Event("Cloud Computing Seminar", "Seminar", "Apr", "18", "2:00 PM", "Auditorium A", 78, 100, "Explore the future of scalable cloud infrastructure...", Color(0xFF00B0FF)),
    Event("Cybersecurity Webinar", "Webinar", "Apr", "22", "3:00 PM", "Online", 189, 300, "Learn practical techniques to defend systems from cyber threats...", Color(0xFF2979FF)),
    Event("AI/ML Competition", "Competition", "Apr", "25", "10:00 AM", "Data Science Lab", 65, 80, "Showcase your artificial intelligence models in this competition...", Color(0xFFFFD600)),
    Event("Mobile App Development", "Workshop", "Apr", "28", "1:00 PM", "Tech Hub, Room 205", 38, 40, "Build cross-platform applications using modern frameworks...", Color(0xFFC583FF)),
    Event("Blockchain Fundamentals", "Seminar", "May", "02", "11:00 AM", "Seminar Hall B", 45, 120, "An in-depth session on decentralized ledger technologies...", Color(0xFF00B0FF)),
    Event("Badminton", "Fun Match", "May", "05", "8:00 AM", "Open Space", 6, 100, "Friendly weekend sports matches with fellow tech students...", Color(0xFFFF8A80))
)