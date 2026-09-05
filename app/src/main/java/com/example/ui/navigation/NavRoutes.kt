package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Trips : Screen("trips")
    object MysteryTrip : Screen("mystery_trip")
    object AIPlanner : Screen("ai_planner")
    object Bookings : Screen("bookings")
    object Chat : Screen("chat")
    object AddExperience : Screen("add_experience")
    object AdminDashboard : Screen("admin_dashboard")
    object About : Screen("about")
    object AcademicReport : Screen("academic_report")
    object ExperienceDetail : Screen("experience_detail/{experienceId}") {
        fun createRoute(experienceId: String) = "experience_detail/$experienceId"
    }
}

enum class MainTab(val titleAr: String, val route: String, val icon: String) {
    HOME("الرئيسية", Screen.Home.route, "home"),
    TRIPS("الرحلات", Screen.Trips.route, "explore"),
    AI_PLANNER("المستشار الذكي", Screen.AIPlanner.route, "auto_awesome"),
    BOOKINGS("حجوزاتي", Screen.Bookings.route, "calendar_today"),
    CHAT("المحادثات", Screen.Chat.route, "chat"),
    ABOUT("حول المشروع", Screen.About.route, "info")
}
