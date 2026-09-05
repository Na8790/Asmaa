package com.example.data.repository

import com.example.data.local.TajribahDao
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class TajribahRepository(private val dao: TajribahDao) {

    val experiences: Flow<List<Experience>> = dao.getAllExperiences()
    val trips: Flow<List<Trip>> = dao.getAllTrips()
    val bookings: Flow<List<Booking>> = dao.getAllBookings()

    suspend fun initializeSeedData() {
        val currentExp = dao.getAllExperiences().first()
        if (currentExp.isEmpty()) {
            val seedExperiences = listOf(
                Experience(
                    id = "exp_01",
                    title = "يوم كامل مع نحّال في مناحل العسل الجبلي",
                    category = ExperienceCategory.BEEKEEPING,
                    hostName = "العم ناصر الحميري",
                    hostTitle = "خبير تربية النحل وإنتاج عسل السدر الطبيعي (خبرة 30 عاماً)",
                    city = "وصاب العالي",
                    locationDescription = "الوديان المزهرة - قرية المناحل",
                    pricePerPerson = 45.0,
                    durationHours = 6,
                    maxGroupSize = 6,
                    rating = 4.9,
                    reviewCount = 128,
                    description = "عش يوماً استثنائياً بين خلايا النحل الجبلي العريقة. ارتدِ بدلة الحماية وشارك العم ناصر في فحص الخلايا، تذوق عسل السدر الصافي مباشرة من القرص، وتعرّف على أسرار النباتات العطرية التي يتغذى عليها النحل.",
                    requirements = "حذاء للمشي الجبلي، ملابس مريحة، حب الاستكشاف (تتوفر بدلات الحماية المعتمدة)",
                    imageUrl = "https://images.unsplash.com/photo-1587049352851-8d4e89133924?w=800&auto=format&fit=crop&q=80",
                    isFeatured = true
                ),
                Experience(
                    id = "exp_02",
                    title = "موسم قطاف البن اليمني الأصيل في مدرجات حراز",
                    category = ExperienceCategory.COFFEE_CRAFT,
                    hostName = "م. فؤاد الحرازي",
                    hostTitle = "مزارع وباحث في سلالات القهوة المختصة العتيقة",
                    city = "مناخة - حراز",
                    locationDescription = "مدرجات البن الشاهقة على ارتفاع 2200م",
                    pricePerPerson = 55.0,
                    durationHours = 7,
                    maxGroupSize = 8,
                    rating = 5.0,
                    reviewCount = 94,
                    description = "رحلة إلى قلب مهد القهوة العالمية. ستصعد بين المدرجات الصخرية التاريخية، تقطف كرز البن الأحمر الناضج بيدك، تتعلم طريقة التجفيف التقليدية على الأسطح الحجرية، وتختم اليوم بجلسة تحميص واحتساء قهوة القشر التراثية على نار الحطب.",
                    requirements = "لياقة بدنية معتدلة، قبعة شمسية، كاميرا لالتقاط الإطلالات البانورامية",
                    imageUrl = "https://images.unsplash.com/photo-1498804103079-a6351b050096?w=800&auto=format&fit=crop&q=80",
                    isFeatured = true
                ),
                Experience(
                    id = "exp_03",
                    title = "أسرار المطبخ الصنعاني وصناعة السلتة والسبايا",
                    category = ExperienceCategory.TRADITIONAL_COOKING,
                    hostName = "أم عبد الرحمن الصنعانية",
                    hostTitle = "طاهية شعبية وراعية ولائم الضيافة التاريخية",
                    city = "صنعاء القديمة",
                    locationDescription = "بيت حجري أثري مطل على البستان",
                    pricePerPerson = 35.0,
                    durationHours = 4,
                    maxGroupSize = 5,
                    rating = 4.8,
                    reviewCount = 210,
                    description = "انضم إلى مطبخ عائلي دافئ في قلب المدينة القديمة. ستتعلم تحضير السلتة في المقالي الحجرية البركانية، وعجن وفرد السبايا الذهبية بالسمن البلدي والعسل، مع سماع حكايات وتاريخ المائدة العربية الأصيلة.",
                    requirements = "شهية طيبة، والاهتمام بفنون الطبخ والتوابل الطبيعية",
                    imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800&auto=format&fit=crop&q=80",
                    isFeatured = true
                ),
                Experience(
                    id = "exp_04",
                    title = "فنون تشكيل الفخار اليدوي على الدولاب الحرفي",
                    category = ExperienceCategory.POTTERY,
                    hostName = "الأستاذ حمود الخزاف",
                    hostTitle = "شيخ حرفة صناعة الأواني والجرار الخزفية التراثية",
                    city = "حيس - زبيد",
                    locationDescription = "مشغل الخزف التراثي الطيني",
                    pricePerPerson = 30.0,
                    durationHours = 3,
                    maxGroupSize = 6,
                    rating = 4.7,
                    reviewCount = 76,
                    description = "ضع يديك في الطين النقي واشعر بحركة الدولاب الدائري وهو يصنع إناءً أو كوباً قهوة أثرياً من إبداعك. ستتعلم أسرار الحرق في الفرن التقليدي وتأخذ تحفتك الطينية معك كتذكار حي لا يُنسى.",
                    requirements = "ملابس يمكن غسلها بسهولة (المواد طينية طبيعية غير ضارة)",
                    imageUrl = "https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=800&auto=format&fit=crop&q=80",
                    isFeatured = false
                ),
                Experience(
                    id = "exp_05",
                    title = "رحلة الصيد فجراً مع بحار تقليدي في عرض البحر",
                    category = ExperienceCategory.FISHING,
                    hostName = "الربان سعيد المخاوي",
                    hostTitle = "نوخذة وبحار ورث قراءة التيارات والنجوم أباً عن جد",
                    city = "شواطئ المخا",
                    locationDescription = "المرسى القديم والخلجان الهادئة",
                    pricePerPerson = 60.0,
                    durationHours = 5,
                    maxGroupSize = 4,
                    rating = 4.9,
                    reviewCount = 62,
                    description = "انطلق في قارب الصيد الخشبي مع نسائم الفجر الأولى قبل شروق الشمس. تعلّم رمي شباك الجل وسحب الخيوط، استمتع بمشاهدة أسراب النوارس، ثم اشوِ صيدك الطازج على الفحم مباشرة على رمال الشاطئ الذهبي.",
                    requirements = "سترة نجاة متوفرة، سترة خفيفة مضادة للرياح، نظارة شمسية",
                    imageUrl = "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=800&auto=format&fit=crop&q=80",
                    isFeatured = true
                ),
                Experience(
                    id = "exp_06",
                    title = "تخييم الملاحة الفلكية ومراقبة النجوم في الصحراء",
                    category = ExperienceCategory.CAMPING,
                    hostName = "طارق الكندي",
                    hostTitle = "مرشد صحراوي وفلكي هاوٍ",
                    city = "رملة السبعتين",
                    locationDescription = "مخيم الكثبان الهادئة",
                    pricePerPerson = 80.0,
                    durationHours = 14,
                    maxGroupSize = 10,
                    rating = 5.0,
                    reviewCount = 145,
                    description = "ليلة سحرية تحت سماء مرصعة بملايين النجوم بعيداً عن أضواء المدن. جلسة سمر حول نار السمر، سرد قصص النجوم وقوافل التجارة القديمة، استخدام التلسكوب لرصد الكواكب، ومبيت في خيام بدوية أصيلة.",
                    requirements = "ملابس شتوية دافئة لليل الصحراء، مصباح يدوي",
                    imageUrl = "https://images.unsplash.com/photo-1506535772317-9fdb71c959c6?w=800&auto=format&fit=crop&q=80",
                    isFeatured = true
                )
            )
            dao.insertExperiences(seedExperiences)
        }

        val currentTrips = dao.getAllTrips().first()
        if (currentTrips.isEmpty()) {
            val seedTrips = listOf(
                Trip(
                    id = "trip_01",
                    title = "مسار قوافل اللبان وحصون وادي دوعن التاريخية",
                    city = "حضرموت - دوعن",
                    price = 220.0,
                    durationDays = 3,
                    guideName = "المرشد المهندس عمر باحارثة",
                    transportationType = "سيارة دفع رباعي VIP مكيفة",
                    itinerarySummary = "اليوم 1: قصر البقشان وقرى مدر – اليوم 2: بساتين النخيل ومناحل دوعن – اليوم 3: شبام حضرموت مانهاتن الصحراء",
                    rating = 4.9,
                    reviewCount = 82,
                    imageUrl = "https://images.unsplash.com/photo-1512632570417-a6096a607759?w=800&auto=format&fit=crop&q=80"
                ),
                Trip(
                    id = "trip_02",
                    title = "رحلة الطبيعة البكر: وادي بنا وجبال اللواء الأخضر",
                    city = "إب - وادي بنا",
                    price = 140.0,
                    durationDays = 2,
                    guideName = "المرشد وضاح الإبي",
                    transportationType = "حافلة سياحية متوسطة مجهزة",
                    itinerarySummary = "اليوم 1: شلالات وادي بنا ومدرجات بعدان – اليوم 2: جولة في معاصر الزيوت الحرفية وقرية دار شرف",
                    rating = 4.8,
                    reviewCount = 65,
                    imageUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800&auto=format&fit=crop&q=80"
                ),
                Trip(
                    id = "trip_03",
                    title = "الرحلة المفاجئة الكبرى: مغامرة سياحية مبهمة في أعالي القمم",
                    city = "وجهة سرية (تُكشف قبل الموعد بـ 24 ساعة)",
                    price = 180.0,
                    durationDays = 2,
                    guideName = "فريق المرشدين السريين لمنصة تِجربة",
                    transportationType = "دفع رباعي مجهزة للتضاريس الوعرة",
                    itinerarySummary = "تتضمن: مكان إقامة غير متوقع، تجربة مهنية حصرية، ومسار استكشاف سري مع حقيبة ألغاز ومعدات استكشاف",
                    rating = 5.0,
                    reviewCount = 49,
                    isSurpriseTrip = true,
                    imageUrl = "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?w=800&auto=format&fit=crop&q=80"
                )
            )
            dao.insertTrips(seedTrips)
        }
    }

    suspend fun createBooking(
        itemType: String,
        itemId: String,
        itemTitle: String,
        date: String,
        timeSlot: String,
        guestCount: Int,
        totalPrice: Double,
        paymentMethod: String
    ): Booking {
        val ref = "TJR-" + (10000..99999).random()
        val booking = Booking(
            id = "bk_${System.currentTimeMillis()}",
            itemType = itemType,
            itemId = itemId,
            itemTitle = itemTitle,
            date = date,
            timeSlot = timeSlot,
            guestCount = guestCount,
            totalPrice = totalPrice,
            status = "CONFIRMED",
            bookingReference = ref,
            paymentMethod = paymentMethod
        )
        dao.insertBooking(booking)
        return booking
    }

    suspend fun cancelBooking(bookingId: String) {
        dao.updateBookingStatus(bookingId, "CANCELLED")
    }

    suspend fun addCustomExperience(experience: Experience) {
        dao.insertExperience(experience)
    }

    fun getLocalGuides(): List<LocalGuide> {
        return listOf(
            LocalGuide("g1", "المرشد وليد السقاف", "تاريخ وعمارة صنعاء القديمة", 4.9, "العربية، الإنجليزية", "صنعاء", 40.0),
            LocalGuide("g2", "سارة اليماني", "مسارات الجبال والتوثيق البيئي", 5.0, "العربية، الفرنسية", "تعز / إب", 45.0),
            LocalGuide("g3", "عادل الكثيري", "تراث حضرموت والحرف العريقة", 4.8, "العربية، الألمانية", "سيئون / تريم", 50.0)
        )
    }

    fun getRentalCars(): List<RentalCar> {
        return listOf(
            RentalCar("c1", "تويوتا لاندكروزر V8 صالون مجهز", "دفع رباعي 4x4", 85.0, true, "صنعاء / مأرب"),
            RentalCar("c2", "نيسان باترول سوبر سفاري جبلي", "دفع رباعي صحراوي", 75.0, true, "حضرموت / المهرة"),
            RentalCar("c3", "هيونداي H1 فان سياحي مريح", "حافلة عائلية 8 ركاب", 60.0, true, "إب / تعز")
        )
    }

    fun getSouvenirs(): List<SouvenirItem> {
        return listOf(
            SouvenirItem("s1", "عسل سدر دوعني ملكي معبأ في جرة فخارية", "من مناحل دوعن التراثية", 65.0, "منتجات طبيعية", "https://images.unsplash.com/photo-1587049352851-8d4e89133924?w=600"),
            SouvenirItem("s2", "بن يمني إسماعيلي حبة كاملة فاخرة (500 جم)", "محامص حراز العتيقة", 28.0, "بن أصيل", "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=600"),
            SouvenirItem("s3", "طقم فناجين قهوة فخارية مصنوعة يدوياً", "مشاغل حيس الطينية", 22.0, "حرف يدوية", "https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=600"),
            SouvenirItem("s4", "مبخرة حجرية منحوتة بزخارف سبئية قديمة", "نحاتو الحجر التراثي", 35.0, "تحف وهدايا", "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?w=600")
        )
    }

    fun getSampleReviews(): List<Review> {
        return listOf(
            Review("r1", "exp_01", "أحمد الشامي", 5, "تجربة النحالة غيرت نظرتي تماماً لصناعة العسل! العم ناصر كان مضيافاً وشرح لنا كل تفصيلة بحب.", "قبل يومين", "أهلاً بك يا بني ويسعدنا دائماً استضافتك في مناحل وصاب العالية!"),
            Review("r2", "exp_02", "مريم العباسي", 5, "شرب القهوة بين أشجار البن على نار الحطب كان حلماً تحقق. التنظيم مع المنصة كان احترافياً وسلساً.", "قبل أسبوع", "سعداء جداً بزيارتك ونتطلع لرؤيتك في موسم الحصاد القادم."),
            Review("r3", "exp_03", "طارق زاهر", 5, "أم عبد الرحمن رمز للكرم اليمني، والسلتة بالسبايا كانت ألذ ما تذوقت في حياتي!", "قبل أسبوعين")
        )
    }
}
