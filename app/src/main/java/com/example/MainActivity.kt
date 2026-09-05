package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.data.local.TajribahDatabase
import com.example.data.model.Booking
import com.example.data.model.Experience
import com.example.data.model.Review
import com.example.data.model.Trip
import com.example.data.repository.TajribahRepository
import com.example.ui.components.ExitConfirmDialog
import com.example.ui.components.TajribahBottomNavBar
import com.example.ui.navigation.MainTab
import com.example.ui.navigation.Screen
import com.example.ui.screens.*
import com.example.ui.theme.TajribahTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = TajribahDatabase.getDatabase(this)
        val repository = TajribahRepository(database.dao())

        setContent {
            TajribahTheme {
                TajribahApp(
                    repository = repository,
                    onExitApp = { finish() }
                )
            }
        }
    }
}

@Composable
fun TajribahApp(
    repository: TajribahRepository,
    onExitApp: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Splash.route

    var userRole by remember { mutableStateOf("EXPLORER") } // EXPLORER, HOST, ADMIN
    var showExitDialog by remember { mutableStateOf(false) }

    // Seed Data Initialization
    LaunchedEffect(Unit) {
        repository.initializeSeedData()
    }

    // State Collection
    val experiences by repository.experiences.collectAsState(initial = emptyList())
    val trips by repository.trips.collectAsState(initial = emptyList())
    val bookings by repository.bookings.collectAsState(initial = emptyList())
    val guides = remember { repository.getLocalGuides() }
    val cars = remember { repository.getRentalCars() }
    val souvenirs = remember { repository.getSouvenirs() }
    val sampleReviews = remember { repository.getSampleReviews() }
    val userReviews = remember { mutableStateListOf<Review>() }

    // Active Booking Dialog State
    var activeBookingRequest by remember {
        mutableStateOf<BookingDialogData?>(null)
    }

    // Bottom Bar Visibility Logic
    val isBottomBarVisible = currentRoute in listOf(
        Screen.Home.route,
        Screen.Trips.route,
        Screen.AIPlanner.route,
        Screen.Bookings.route,
        Screen.Chat.route,
        Screen.About.route
    )

    // Back Handler: Exit confirm on Home, otherwise navigate back
    BackHandler(enabled = true) {
        if (currentRoute == Screen.Home.route) {
            showExitDialog = true
        } else if (currentRoute == Screen.Splash.route || currentRoute == Screen.Auth.route) {
            showExitDialog = true
        } else {
            navController.popBackStack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                TajribahBottomNavBar(
                    currentRoute = currentRoute,
                    onTabSelected = { targetRoute ->
                        navController.navigate(targetRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route,
                modifier = Modifier.fillMaxSize()
            ) {
                // Splash Screen
                composable(Screen.Splash.route) {
                    SplashScreen(
                        onSplashFinished = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    )
                }

                // Auth Screen
                composable(Screen.Auth.route) {
                    AuthScreen(
                        onAuthSuccess = { role ->
                            userRole = role
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Auth.route) { inclusive = true }
                            }
                        }
                    )
                }

                // Home Screen
                composable(Screen.Home.route) {
                    HomeScreen(
                        experiences = experiences,
                        trips = trips,
                        localGuides = guides,
                        rentalCars = cars,
                        souvenirs = souvenirs,
                        userRole = userRole,
                        onExperienceClick = { id ->
                            navController.navigate(Screen.ExperienceDetail.createRoute(id))
                        },
                        onTripsClick = {
                            navController.navigate(Screen.Trips.route)
                        },
                        onMysteryTripClick = {
                            navController.navigate(Screen.MysteryTrip.route)
                        },
                        onAIPlannerClick = {
                            navController.navigate(Screen.AIPlanner.route)
                        },
                        onAdminClick = {
                            navController.navigate(Screen.AdminDashboard.route)
                        },
                        onAddExperienceClick = {
                            navController.navigate(Screen.AddExperience.route)
                        }
                    )
                }

                // Experience Detail Screen
                composable(
                    route = Screen.ExperienceDetail.route,
                    arguments = listOf(navArgument("experienceId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val expId = backStackEntry.arguments?.getString("experienceId")
                    val selectedExp = experiences.firstOrNull { it.id == expId }
                    val allReviews = sampleReviews + userReviews.filter { it.targetId == expId }

                    ExperienceDetailScreen(
                        experience = selectedExp,
                        reviews = allReviews,
                        onBack = { navController.popBackStack() },
                        onStartChatWithHost = { hostName ->
                            navController.navigate(Screen.Chat.route)
                        },
                        onBookClick = { exp, guests, date, slot ->
                            activeBookingRequest = BookingDialogData(
                                itemType = "EXPERIENCE",
                                itemId = exp.id,
                                itemTitle = exp.title,
                                pricePerPerson = exp.pricePerPerson,
                                guests = guests,
                                date = date,
                                timeSlot = slot
                            )
                        },
                        onAddReview = { rating, comment ->
                            if (expId != null) {
                                userReviews.add(
                                    Review(
                                        id = "rev_${System.currentTimeMillis()}",
                                        targetId = expId,
                                        userName = "أنت (مسافر معتمد)",
                                        rating = rating,
                                        comment = comment,
                                        date = "اليوم"
                                    )
                                )
                            }
                        }
                    )
                }

                // Trips Screen
                composable(Screen.Trips.route) {
                    TripsScreen(
                        trips = trips,
                        onMysteryTripClick = {
                            navController.navigate(Screen.MysteryTrip.route)
                        },
                        onBookTrip = { trip ->
                            activeBookingRequest = BookingDialogData(
                                itemType = "TRIP",
                                itemId = trip.id,
                                itemTitle = trip.title,
                                pricePerPerson = trip.price,
                                guests = 1,
                                date = "الأسبوع القادم، الجمعة",
                                timeSlot = "رحلة مجدولة (${trip.durationDays} أيام)"
                            )
                        }
                    )
                }

                // Mystery Trip Dedicated Screen
                composable(Screen.MysteryTrip.route) {
                    MysteryTripScreen(
                        onBack = { navController.popBackStack() },
                        onBookMysteryTrip = { budget, thrill, guests ->
                            coroutineScope.launch {
                                repository.createBooking(
                                    itemType = "MYSTERY_TRIP",
                                    itemId = "mystery_trip_auto",
                                    itemTitle = "رحلة مفاجئة: $thrill",
                                    date = "تُحدد بالاتفاق السري",
                                    timeSlot = "سرية بالكامل",
                                    guestCount = guests,
                                    totalPrice = budget * guests,
                                    paymentMethod = "محفظة إلكترونية محلية"
                                )
                            }
                        }
                    )
                }

                // AI Travel Planner Screen
                composable(Screen.AIPlanner.route) {
                    AITravelPlannerScreen(
                        onBookItinerary = { title, cost ->
                            activeBookingRequest = BookingDialogData(
                                itemType = "AI_ITINERARY",
                                itemId = "ai_plan_${System.currentTimeMillis()}",
                                itemTitle = title,
                                pricePerPerson = cost,
                                guests = 1,
                                date = "وفق الخطة الذكية المقترحة",
                                timeSlot = "برنامج متكامل متعدد الأيام"
                            )
                        }
                    )
                }

                // Bookings History Screen
                composable(Screen.Bookings.route) {
                    BookingsScreen(
                        bookings = bookings,
                        onCancelBooking = { id ->
                            coroutineScope.launch {
                                repository.cancelBooking(id)
                            }
                        },
                        onExploreClick = {
                            navController.navigate(Screen.Home.route)
                        }
                    )
                }

                // Chat Screen
                composable(Screen.Chat.route) {
                    ChatScreen()
                }

                // Admin Dashboard Screen
                composable(Screen.AdminDashboard.route) {
                    AdminDashboardScreen(
                        experiencesCount = experiences.size,
                        tripsCount = trips.size,
                        bookingsCount = bookings.size,
                        onBack = { navController.popBackStack() }
                    )
                }

                // Add Experience Screen (for Hosts)
                composable(Screen.AddExperience.route) {
                    AddExperienceScreen(
                        onBack = { navController.popBackStack() },
                        onSubmit = { newExp ->
                            coroutineScope.launch {
                                repository.addCustomExperience(newExp)
                            }
                        }
                    )
                }

                // About Screen
                composable(Screen.About.route) {
                    AboutScreen(
                        onOpenAcademicReport = {
                            navController.navigate(Screen.AcademicReport.route)
                        }
                    )
                }

                // Academic Graduation Project Report Screen
                composable(Screen.AcademicReport.route) {
                    AcademicReportScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }

    // Active Booking Confirmation Flow Dialog
    if (activeBookingRequest != null) {
        val req = activeBookingRequest!!
        BookingConfirmationDialog(
            itemTitle = req.itemTitle,
            pricePerPerson = req.pricePerPerson,
            guestCount = req.guests,
            date = req.date,
            timeSlot = req.timeSlot,
            onConfirmBooking = { paymentMethod, totalPrice ->
                coroutineScope.launch {
                    repository.createBooking(
                        itemType = req.itemType,
                        itemId = req.itemId,
                        itemTitle = req.itemTitle,
                        date = req.date,
                        timeSlot = req.timeSlot,
                        guestCount = req.guests,
                        totalPrice = totalPrice,
                        paymentMethod = paymentMethod
                    )
                    activeBookingRequest = null
                    navController.navigate(Screen.Bookings.route)
                }
            },
            onDismiss = { activeBookingRequest = null }
        )
    }

    // Exit Confirmation Dialog
    if (showExitDialog) {
        ExitConfirmDialog(
            onConfirm = {
                showExitDialog = false
                onExitApp()
            },
            onDismiss = { showExitDialog = false }
        )
    }
}

data class BookingDialogData(
    val itemType: String,
    val itemId: String,
    val itemTitle: String,
    val pricePerPerson: Double,
    val guests: Int,
    val date: String,
    val timeSlot: String
)
