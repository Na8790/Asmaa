package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Platform Core Models for Tajribah (تِجربة)
 * Designed by Engineer Raghad
 */

enum class ExperienceCategory(val titleAr: String, val titleEn: String, val iconName: String) {
    BEEKEEPING("يوم مع نحّال", "Beekeeping Day", "eco"),
    FARMING("يوم مع مزارع", "Farmer Life", "agriculture"),
    FISHING("يوم مع صياد", "Fisherman Day", "sailing"),
    TRADITIONAL_COOKING("طاهية شعبية", "Local Culinary", "restaurant"),
    POTTERY("صناعة الفخار", "Artisan Pottery", "brush"),
    BREAD_MAKING("صناعة الخبز البلدي", "Traditional Bread", "bakery_dining"),
    COFFEE_CRAFT("صناعة وتحميص البن", "Heritage Coffee", "local_cafe"),
    HANDICRAFTS("الأعمال اليدوية", "Handicrafts", "handyman"),
    HORSE_RIDING("ركوب الخيل", "Horseback Riding", "pets"),
    CAMPING("التخييم الصحراوي والجبلي", "Camping", "terrain"),
    MOUNTAIN_HIKING("صعود الجبال", "Mountain Hiking", "hiking")
}

@Entity(tableName = "experiences")
data class Experience(
    @PrimaryKey val id: String,
    val title: String,
    val category: ExperienceCategory,
    val hostName: String,
    val hostTitle: String,
    val city: String,
    val locationDescription: String,
    val pricePerPerson: Double,
    val durationHours: Int,
    val maxGroupSize: Int,
    val rating: Double,
    val reviewCount: Int,
    val description: String,
    val requirements: String,
    val imageUrl: String,
    val availableDates: String = "يومياً من 9:00 ص إلى 4:00 م",
    val isVerified: Boolean = true,
    val isFeatured: Boolean = false
)

@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey val id: String,
    val title: String,
    val city: String,
    val price: Double,
    val durationDays: Int,
    val guideName: String,
    val transportationType: String,
    val itinerarySummary: String,
    val rating: Double,
    val reviewCount: Int,
    val isSurpriseTrip: Boolean = false,
    val imageUrl: String
)

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey val id: String,
    val itemType: String, // "EXPERIENCE" or "TRIP" or "SURPRISE"
    val itemId: String,
    val itemTitle: String,
    val date: String,
    val timeSlot: String,
    val guestCount: Int,
    val totalPrice: Double,
    val status: String, // "CONFIRMED", "COMPLETED", "CANCELLED"
    val bookingReference: String,
    val paymentMethod: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey val id: String,
    val targetId: String,
    val userName: String,
    val rating: Int,
    val comment: String,
    val date: String,
    val hostReply: String? = null,
    val likesCount: Int = 0
)

data class ChatMessage(
    val id: String,
    val senderName: String,
    val isFromUser: Boolean,
    val text: String,
    val time: String,
    val isRead: Boolean = true
)

data class LocalGuide(
    val id: String,
    val name: String,
    val specialty: String,
    val rating: Double,
    val languages: String,
    val city: String,
    val dailyRate: Double,
    val verifiedBadge: Boolean = true
)

data class RentalCar(
    val id: String,
    val modelName: String,
    val type: String, // SUV, 4x4 Desert, Sedan
    val pricePerDay: Double,
    val withDriver: Boolean,
    val city: String
)

data class SouvenirItem(
    val id: String,
    val title: String,
    val artisanName: String,
    val price: Double,
    val category: String,
    val imageUrl: String
)

data class AITravelPlanRequest(
    val budget: Double = 500.0,
    val travelerCount: Int = 2,
    val durationDays: Int = 3,
    val city: String = "صنعاء القديمة",
    val interests: List<String> = listOf("تراث وحرف", "طعام محلي", "تخييم"),
    val tripType: String = "ثقافي واكتشاف",
    val luxuryLevel: String = "متوسط مميز",
    val transportNeeded: String = "سيارة دفع رباعي مع سائق محلي"
)

data class PlanDay(
    val dayNumber: Int,
    val title: String,
    val morningActivity: String,
    val lunchRecommendation: String,
    val afternoonExperience: String,
    val eveningCamp: String,
    val estimatedDayCost: Double
)

data class AITravelPlanResult(
    val summary: String,
    val city: String,
    val totalEstimatedCost: Double,
    val days: List<PlanDay>,
    val recommendedExperiences: List<String>,
    val transportationNote: String,
    val disclaimer: String = "تخطيط ذكي تقديري مبني على خدمات منصة تِجربة المعتمدة. الحجز والدفع يخضع لتوفر المقاعد وتأكيد الشركاء."
)
