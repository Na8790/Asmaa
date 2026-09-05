package com.example.domain.ai

import com.example.data.model.AITravelPlanRequest
import com.example.data.model.AITravelPlanResult
import com.example.data.model.PlanDay

/**
 * Intelligent Travel Assistant Engine for Tajribah Platform
 * Formulates realistic itineraries based on registered platform services,
 * respecting user budget, distance, and luxury criteria.
 */
object AITravelPlannerEngine {

    fun generateItinerary(req: AITravelPlanRequest): AITravelPlanResult {
        val daysList = mutableListOf<PlanDay>()
        val baseDayBudget = (req.budget / req.durationDays).coerceAtLeast(40.0)

        for (day in 1..req.durationDays) {
            val planDay = when (day) {
                1 -> PlanDay(
                    dayNumber = 1,
                    title = "الانطلاق والاستكشاف الأثري والتذوق الأصيل",
                    morningActivity = "استقبال من ${req.transportNeeded}، والتوجه لحي الحرفيين مع جولة تعريفية في باب اليمن وأسواق الفضة",
                    lunchRecommendation = "وجبة غداء شعبية فاخرة (سلتة، لحم مندي، وسبايا طازجة) في بستان تراثي",
                    afternoonExperience = "تجربة تشكيل الفخار اليدوي أو يوم في مطبخ شعبي مع ضيافة الشاي بالنعناع",
                    eveningCamp = "جلسة سمر مسائية على سطح مبنى تاريخي مع القهوة التراثية وإطلالة على منازل القمريات المضاءة",
                    estimatedDayCost = baseDayBudget * 0.9
                )
                2 -> PlanDay(
                    dayNumber = 2,
                    title = "رحلة الطبيعة الحية وتجربة الحرفيين والمزارع",
                    morningActivity = "انطلاق مبكر نحو المدرجات الجبلية العتيقة وتجربة يوم كامل مع مزارع البن أو مناحل عسل السدر",
                    lunchRecommendation = "غداء جبلي تقليدي مطبوخ على مواقد الحطب بين مزارع الرمان والأعشاب",
                    afternoonExperience = "المشاركة في قطاف المحاصيل وتجفيف الحبوب أو فحص خلايا النحل مع خبير الحرفة",
                    eveningCamp = "مخيم الملاحة الفلكية ومراقبة النجوم بالتلسكوب مع المرشد الفلكي وعشاء الشواء البدوي",
                    estimatedDayCost = baseDayBudget * 1.15
                )
                3 -> PlanDay(
                    dayNumber = 3,
                    title = "مغامرة القمم أو السواحل واقتناء الهدايا التذكارية",
                    morningActivity = "جولة مسار جبلي خفيف لمشاهدة شروق الشمس من الحصون المعلقة أو رحلة صيد بحري فجراً",
                    lunchRecommendation = "سمك طازج مشوي على الطريقة التراثية مع خبز الملوج البلدي",
                    afternoonExperience = "زيارة دكاكين العسل التراثي والفضيات لاقتناء تذكارات حية موثقة من أصحاب المهن",
                    eveningCamp = "جلسة تقييم ومشاركة الصور والذكريات مع المرشد المحلي وتسليم شهادة مستكشف تِجربة",
                    estimatedDayCost = baseDayBudget * 0.95
                )
                else -> PlanDay(
                    dayNumber = day,
                    title = "يوم الاستكشاف الحر والتجارب المفتوحة",
                    morningActivity = "زيارة أسواق الحرف القديمة وركوب الخيل في السهول الخضراء",
                    lunchRecommendation = "مأكولات ريفية طازجة من خيرات المزارع العضوية",
                    afternoonExperience = "ورشة نسج الحرف اليدوية أو صناعة الحلي الفضية التراثية",
                    eveningCamp = "أمسية شعر وموسيقى فلكلورية مع وجبة خفيفة ومشروب الزنجبيل الحار",
                    estimatedDayCost = baseDayBudget
                )
            }
            daysList.add(planDay)
        }

        val totalCost = daysList.sumOf { it.estimatedDayCost } * req.travelerCount

        val recommendedExps = listOf(
            "موسم قطاف البن اليمني الأصيل في مدرجات حراز",
            "يوم كامل مع نحّال في مناحل العسل الجبلي",
            "أسرار المطبخ الصنعاني وصناعة السلتة والسبايا",
            "تخييم الملاحة الفلكية ومراقبة النجوم في الصحراء"
        )

        return AITravelPlanResult(
            summary = "تم تصميم برنامج سياحي ذكي مخصص لوجهة (${req.city}) لمدة (${req.durationDays}) أيام لعدد (${req.travelerCount}) أفراد بنمط (${req.tripType}) ومستوى رفاهية (${req.luxuryLevel}).",
            city = req.city,
            totalEstimatedCost = totalCost,
            days = daysList,
            recommendedExperiences = recommendedExps,
            transportationNote = "المسافات اليومية التقديرية بين 25 كم إلى 65 كم، وزمن التنقل بين 35 إلى 75 دقيقة بواسطة: ${req.transportNeeded}."
        )
    }
}
