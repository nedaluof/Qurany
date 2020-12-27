package com.nedaluof.qurany.util

import com.nedaluof.qurany.data.model.Sura

/**
 * Created by nedaluof on 6/16/2020. {Java}
 * Created by nedaluof on 12/17/2020. {Kotlin}
 */
object SuraUtil {


    fun getArabicSuraName() = listOf(
            Sura(1, " الفاتحة"),
            Sura(2, "البقرة"),
            Sura(3, "ال عمران "),
            Sura(4, "النساء"), Sura(5, " المائدة"), Sura(6, " الانعام"), Sura(7, " الأعراف"), Sura(8, " الأنفال"), Sura(9, " التوبة "), Sura(10, " يونس"), Sura(11, " هود"), Sura(12, " يوسف"), Sura(13, " الرعد"), Sura(14, " إبراهيم"), Sura(15, " الحجر"), Sura(16, " النحل"), Sura(17, " الإسراء"), Sura(18, " الكهف"), Sura(19, " مريم"), Sura(20, " طه"), Sura(21, " الأنبياء"), Sura(22, " الحج"), Sura(23, " المؤمنون"), Sura(24, " النّور"), Sura(25, "  الفرقان "), Sura(26, "  الشعراء "), Sura(27, " النّمل"), Sura(28, " القصص"), Sura(29, " العنكبوت"), Sura(30, " الرّوم"), Sura(31, " لقمان"), Sura(32, " السجدة"), Sura(33, " الأحزاب"), Sura(34, " سبأ"), Sura(35, " فاطر"), Sura(36, " يس"), Sura(37, " الصافات"), Sura(38, " ص"), Sura(39, " الزمر"), Sura(40, " غافر"), Sura(41, " فصّلت"), Sura(42, " الشورى"), Sura(43, " الزخرف"), Sura(44, " الدّخان"), Sura(45, " الجاثية"), Sura(46, " الأحقاف"), Sura(47, " محمد"), Sura(48, " الفتح"), Sura(49, " الحجرات"), Sura(50, " ق"), Sura(51, " الذاريات"), Sura(52, " الطور"), Sura(53, " النجم"), Sura(54, " القمر"), Sura(55, " الرحمن"), Sura(56, " الواقعة"), Sura(57, " الحديد"), Sura(58, " المجادلة"), Sura(59, " الحشر"), Sura(60, " الممتحنة"), Sura(61, " الصف"), Sura(62, " الجمعة"), Sura(63, " المنافقون"), Sura(64, " التغابن"), Sura(65, " الطلاق"), Sura(66, " التحريم"), Sura(67, " الملك"), Sura(68, " القلم"), Sura(69, " الحاقة"), Sura(70, " المعارج"), Sura(71, " نوح"), Sura(72, " الجن"), Sura(73, " المزّمّل"), Sura(74, " المدّثر"), Sura(75, " القيامة"), Sura(76, " الإنسان"), Sura(77, " المرسلات"), Sura(78, " النبأ"), Sura(79, " النازعات"), Sura(80, " عبس"), Sura(81, " التكوير"), Sura(82, " الإنفطار"), Sura(83, " المطفّفين"), Sura(84, " الإنشقاق"), Sura(85, " البروج"), Sura(86, " الطارق"), Sura(87, " الأعلى"), Sura(88, " الغاشية"), Sura(89, " الفجر"), Sura(90, " البلد"), Sura(91, " الشمس"), Sura(92, " الليل"), Sura(93, " الضحى"), Sura(94, " الشرح"), Sura(95, " التين"), Sura(96, " العلق"), Sura(97, " القدر"), Sura(98, " البينة"), Sura(99, " الزلزلة"), Sura(100, " العاديات"), Sura(101, " القارعة"), Sura(102, " التكاثر"), Sura(103, " العصر"), Sura(104, " الهمزة"), Sura(105, " الفيل"), Sura(106, " قريش"), Sura(107, " الماعون"), Sura(108, " الكوثر"), Sura(109, " الكافرون"), Sura(110, " النصر"), Sura(111, " المسد"), Sura(112, " الإخلاص"), Sura(113, " الفلق"), Sura(114, " النّاس")
    )

    fun getEnglishSuraName() = listOf(
            Sura(1, "Al-Fatihah "),
            Sura(2, "Al-Baqarah "),
            Sura(3, "Al-'Imran "),
            Sura(4, "An-Nisa' "),
            Sura(5, "Al-Ma'idah "),
            Sura(6, "Al-An'am "),
            Sura(7, "Al-A'raf "),
            Sura(8, "Al-Anfal "),
            Sura(9, "Al-Tawba"),
            Sura(10, "Yunus"),
            Sura(11, " Hud(Hud)"),
            Sura(12, " Yusuf "),
            Sura(13, "Ar - Ra'd"),
            Sura(14, "Ibrahim"),
            Sura(15, " Al - Hijr"),
            Sura(16, " An - Nahl"),
            Sura(17, " Al-Israa"),
            Sura(18, " Al-Kahf  "),
            Sura(19, "Maryam "),
            Sura(20, "Ta Ha"),
            Sura(21, "Al-Anbiya' "),
            Sura(22, "Al-Hajj "),
            Sura(23, "Al-Mu'minun"),
            Sura(24, "An-Nur "),
            Sura(25, "Al-Furqan "),
            Sura(26, "Ash-Shu'ara'"),
            Sura(27, "An-Naml "),
            Sura(28, "Al-Qasas "),
            Sura(29, "Al-'Ankabut "),
            Sura(30, "Ar-Rum "),
            Sura(31, "Luqman "),
            Sura(32, "As-Sajdah "),
            Sura(33, "Al-Ahzab "),
            Sura(34, "Al-Saba'"),
            Sura(35, "Al-Fatir"),
            Sura(36, "Ya Sin "),
            Sura(37, "As-Saffat"),
            Sura(38, "Sad "),
            Sura(39, "Az-Zumar "),
            Sura(40, "Ghaffer"),
            Sura(41, "Fusilat"),
            Sura(42, "Ash-Shura "),
            Sura(43, "Az-Zukhruf "),
            Sura(44, "Ad-Dukhan"),
            Sura(45, "Al-Jathiyah"),
            Sura(46, "Al-Ahqaf "),
            Sura(47, "Muhammad "),
            Sura(48, "Al-Fath "),
            Sura(49, "Al-Hujurat "),
            Sura(50, "Qaf  "),
            Sura(51, "Ad-Dhariyat"),
            Sura(52, "At-Tur "),
            Sura(53, "An-Najm "),
            Sura(54, "Al-Qamar "),
            Sura(55, "Ar-Rahman "),
            Sura(56, "Al-Waqi'ah"),
            Sura(57, "Al-Hadid "),
            Sura(58, "Al-Mujadilah"),
            Sura(59, "Al-Hashr "),
            Sura(60, "Al-Mumtahanah "),
            Sura(61, "As-Saff "),
            Sura(62, "Al-Jumu'ah "),
            Sura(63, "Al-Munafiqun "),
            Sura(64, "At-Taghabun "),
            Sura(65, "At-Talaq "),
            Sura(66, "At-Tahrim "),
            Sura(67, "Al-Mulk "),
            Sura(68, "Al-Qalam "),
            Sura(69, "Al-Haqqah "),
            Sura(70, "Al-Ma'arij "),
            Sura(71, "Nuh"),
            Sura(72, "Al-Jinn "),
            Sura(73, "Al-Muzzammil"),
            Sura(74, "Al-Muddaththir "),
            Sura(75, "Al-Qiyamah "),
            Sura(76, "Al-Insan "),
            Sura(77, "Al-Mursalat "),
            Sura(78, "An-Naba'  "),
            Sura(79, "An-Nazi'at "),
            Sura(80, "'Abasa"),
            Sura(81, "At-Takwir"),
            Sura(82, "Al-Infitar "),
            Sura(83, "At-Tatfif "),
            Sura(84, "Al-Inshiqaq "),
            Sura(85, "Al-Buruj "),
            Sura(86, "At-Tariq "),
            Sura(87, "Al-A'la "),
            Sura(88, "Al-Ghashiyah "),
            Sura(89, "Al-Fajr "),
            Sura(90, "Al-Balad "),
            Sura(91, "Ash-Shams "),
            Sura(92, "Al-Lail "),
            Sura(93, "Ad-Duha "),
            Sura(94, "Al-Inshirah "),
            Sura(95, "At-Tin "),
            Sura(96, "Al-'Alaq  "),
            Sura(97, " Al-Qadr "),
            Sura(98, " Al-Bayyinah"),
            Sura(99, " Al-Zilzal  "),
            Sura(100, " Al-'Adiyat "),
            Sura(101, " Al-Qari'ah "),
            Sura(102, "At-Takathur "),
            Sura(103, "Al-'Asr "),
            Sura(104, "Al-Humazah "),
            Sura(105, "Al-Fil "),
            Sura(106, "Al-Quraish "),
            Sura(107, "Al-Ma'un  "),
            Sura(108, "Al-Kauthar "),
            Sura(109, "Al-Kafirun "),
            Sura(110, "An-Nasr "),
            Sura(111, " Al-Lahab "),
            Sura(112, " Al-Ikhlas "),
            Sura(113, "Al-Falaq "),
            Sura(114, " An-Nas  "))

    fun getSuraIndex(id: Int): String {
        return when {
            id < 9 -> {
                "00$id"
            }
            id in 10..99 -> {
                "0$id"
            }
            else -> {
                id.toString()
            }
        }
    }

    fun getPlayerTitle(suraId: Int, reciterName: String): String? {
        return if (Utility.getLanguage() == "_arabic") {
            reciterName + " | " + getArabicSuraName()[suraId - 1].name
        } else {
            reciterName + " | " + getEnglishSuraName()[suraId - 1].name
        }
    }

    fun getSuraName(suraId: Int): String? {
        return if (Utility.getLanguage() == "_arabic") {
            getArabicSuraName()[suraId - 1].name
        } else {
            getEnglishSuraName()[suraId - 1].name
        }
    }


}