package com.putrinadya.miti.utils

import androidx.compose.ui.graphics.Color
import com.putrinadya.miti.domain.model.Event

object DummyData {
    val dummyEvents = listOf(
        Event(
            title = "UI/UX Design Workshop",
            category = "Workshop",
            dayMonth = "Apr",
            year = "2026",
            fullDate = "Apr 15, 2026",
            time = "10:00 AM",
            location = "Tech Hub, Room 301",
            currentParticipants = 42,
            maxParticipants = 50,
            description = "Learn the fundamentals of user interface and experience design with hands-on projects.",
            categoryColor = Color(0xFFC583FF)
        ),
        Event(
            title = "Annual Hackathon 2026",
            category = "Hackathon",
            dayMonth = "Apr",
            year = "2026",
            fullDate = "Apr 20, 2026",
            time = "9:00 AM",
            location = "Innovation Center",
            currentParticipants = 156,
            maxParticipants = 200,
            description = "Join the biggest competitive hackathon of the year...",
            categoryColor = Color(0xFF00E676)
        ),
        Event(
            title = "Cloud Computing Seminar",
            category = "Seminar",
            dayMonth = "Apr",
            year = "2026",
            fullDate = "Apr 18, 2026",
            time = "2:00 PM",
            location = "Auditorium A",
            currentParticipants = 78,
            maxParticipants = 100,
            description = "Explore the future of scalable cloud infrastructure...",
            categoryColor = Color(0xFF00B0FF)
        ),
        Event(
            title = "Cybersecurity Webinar",
            category = "Webinar",
            dayMonth = "Apr",
            year = "2026",
            fullDate = "Apr 22, 2026",
            time = "3:00 PM",
            location = "Online",
            currentParticipants = 189,
            maxParticipants = 300,
            description = "Learn practical techniques to defend systems from cyber threats...",
            categoryColor = Color(0xFF2979FF)
        ),
        Event(
            title = "AI/ML Competition",
            category = "Competition",
            dayMonth = "Apr",
            year = "2026",
            fullDate = "Apr 25, 2026",
            time = "10:00 AM",
            location = "Data Science Lab",
            currentParticipants = 65,
            maxParticipants = 80,
            description = "Showcase your artificial intelligence models in this competition...",
            categoryColor = Color(0xFFFFD600)
        ),
        Event(
            title = "Mobile App Development",
            category = "Workshop",
            dayMonth = "Apr",
            year = "2026",
            fullDate = "Apr 28, 2026",
            time = "1:00 PM",
            location = "Tech Hub, Room 205",
            currentParticipants = 38,
            maxParticipants = 40,
            description = "Build cross-platform applications using modern frameworks...",
            categoryColor = Color(0xFFC583FF)
        ),
        Event(
            title = "Blockchain Fundamentals",
            category = "Seminar",
            dayMonth = "May",
            year = "2026",
            fullDate = "May 02, 2026",
            time = "11:00 AM",
            location = "Seminar Hall B",
            currentParticipants = 45,
            maxParticipants = 120,
            description = "An in-depth session on decentralized ledger technologies...",
            categoryColor = Color(0xFF00B0FF)
        ),
        Event(
            title = "Badminton",
            category = "Fun Match",
            dayMonth = "May",
            year = "2026",
            fullDate = "May 05, 2026",
            time = "8:00 AM",
            location = "Open Space",
            currentParticipants = 6,
            maxParticipants = 100,
            description = "Friendly weekend sports matches with fellow tech students...",
            categoryColor = Color(0xFFFF8A80)
        )
    )
}