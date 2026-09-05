package com.example.data.local

import androidx.room.*
import com.example.data.model.Booking
import com.example.data.model.Experience
import com.example.data.model.Review
import com.example.data.model.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TajribahDao {
    // Experiences
    @Query("SELECT * FROM experiences")
    fun getAllExperiences(): Flow<List<Experience>>

    @Query("SELECT * FROM experiences WHERE id = :id")
    suspend fun getExperienceById(id: String): Experience?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperiences(experiences: List<Experience>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperience(experience: Experience)

    @Delete
    suspend fun deleteExperience(experience: Experience)

    // Trips
    @Query("SELECT * FROM trips")
    fun getAllTrips(): Flow<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrips(trips: List<Trip>)

    // Bookings
    @Query("SELECT * FROM bookings ORDER BY createdAt DESC")
    fun getAllBookings(): Flow<List<Booking>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Booking)

    @Query("UPDATE bookings SET status = :status WHERE id = :bookingId")
    suspend fun updateBookingStatus(bookingId: String, status: String)

    @Query("SELECT COUNT(*) FROM bookings")
    suspend fun getBookingsCount(): Int

    // Reviews
    @Query("SELECT * FROM reviews WHERE targetId = :targetId")
    fun getReviewsForTarget(targetId: String): Flow<List<Review>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: Review)
}
