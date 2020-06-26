package com.nedaluof.qurany.util;

import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.data.model.Suras;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nedaluof on 6/16/2020.
 */
public class SurasUtil {


    public static List<Suras> arabicSurasName() {
        List<Suras> surasList = new ArrayList<>();
        surasList.add(new Suras(1, " الفاتحة"));
        surasList.add(new Suras(2, "البقرة"));
        surasList.add(new Suras(3, "ال عمران "));
        surasList.add(new Suras(4, "النساء"));
        surasList.add(new Suras(5, " المائدة"));
        surasList.add(new Suras(6, " الانعام"));
        surasList.add(new Suras(7, " الأعراف"));
        surasList.add(new Suras(8, " الأنفال"));
        surasList.add(new Suras(9, " التوبة "));
        surasList.add(new Suras(10, " يونس"));
        surasList.add(new Suras(11, " هود"));
        surasList.add(new Suras(12, " يوسف"));
        surasList.add(new Suras(13, " الرعد"));
        surasList.add(new Suras(14, " إبراهيم"));
        surasList.add(new Suras(15, " الحجر"));
        surasList.add(new Suras(16, " النحل"));
        surasList.add(new Suras(17, " الإسراء"));
        surasList.add(new Suras(18, " الكهف"));
        surasList.add(new Suras(19, " مريم"));
        surasList.add(new Suras(20, " طه"));
        surasList.add(new Suras(21, " الأنبياء"));
        surasList.add(new Suras(22, " الحج"));
        surasList.add(new Suras(23, " المؤمنون"));
        surasList.add(new Suras(24, " النّور"));
        surasList.add(new Suras(25, "  الفرقان "));
        surasList.add(new Suras(26, "  الشعراء "));
        surasList.add(new Suras(27, " النّمل"));
        surasList.add(new Suras(28, " القصص"));
        surasList.add(new Suras(29, " العنكبوت"));
        surasList.add(new Suras(30, " الرّوم"));
        surasList.add(new Suras(31, " لقمان"));
        surasList.add(new Suras(32, " السجدة"));
        surasList.add(new Suras(33, " الأحزاب"));
        surasList.add(new Suras(34, " سبأ"));
        surasList.add(new Suras(35, " فاطر"));
        surasList.add(new Suras(36, " يس"));
        surasList.add(new Suras(37, " الصافات"));
        surasList.add(new Suras(38, " ص"));
        surasList.add(new Suras(39, " الزمر"));
        surasList.add(new Suras(40, " غافر"));
        surasList.add(new Suras(41, " فصّلت"));
        surasList.add(new Suras(42, " الشورى"));
        surasList.add(new Suras(43, " الزخرف"));
        surasList.add(new Suras(44, " الدّخان"));
        surasList.add(new Suras(45, " الجاثية"));
        surasList.add(new Suras(46, " الأحقاف"));
        surasList.add(new Suras(47, " محمد"));
        surasList.add(new Suras(48, " الفتح"));
        surasList.add(new Suras(49, " الحجرات"));
        surasList.add(new Suras(50, " ق"));
        surasList.add(new Suras(51, " الذاريات"));
        surasList.add(new Suras(52, " الطور"));
        surasList.add(new Suras(53, " النجم"));
        surasList.add(new Suras(54, " القمر"));
        surasList.add(new Suras(55, " الرحمن"));
        surasList.add(new Suras(56, " الواقعة"));
        surasList.add(new Suras(57, " الحديد"));
        surasList.add(new Suras(58, " المجادلة"));
        surasList.add(new Suras(59, " الحشر"));
        surasList.add(new Suras(60, " الممتحنة"));
        surasList.add(new Suras(61, " الصف"));
        surasList.add(new Suras(62, " الجمعة"));
        surasList.add(new Suras(63, " المنافقون"));
        surasList.add(new Suras(64, " التغابن"));
        surasList.add(new Suras(65, " الطلاق"));
        surasList.add(new Suras(66, " التحريم"));
        surasList.add(new Suras(67, " الملك"));
        surasList.add(new Suras(68, " القلم"));
        surasList.add(new Suras(69, " الحاقة"));
        surasList.add(new Suras(70, " المعارج"));
        surasList.add(new Suras(71, " نوح"));
        surasList.add(new Suras(72, " الجن"));
        surasList.add(new Suras(73, " المزّمّل"));
        surasList.add(new Suras(74, " المدّثر"));
        surasList.add(new Suras(75, " القيامة"));
        surasList.add(new Suras(76, " الإنسان"));
        surasList.add(new Suras(77, " المرسلات"));
        surasList.add(new Suras(78, " النبأ"));
        surasList.add(new Suras(79, " النازعات"));
        surasList.add(new Suras(80, " عبس"));
        surasList.add(new Suras(81, " التكوير"));
        surasList.add(new Suras(82, " الإنفطار"));
        surasList.add(new Suras(83, " المطفّفين"));
        surasList.add(new Suras(84, " الإنشقاق"));
        surasList.add(new Suras(85, " البروج"));
        surasList.add(new Suras(86, " الطارق"));
        surasList.add(new Suras(87, " الأعلى"));
        surasList.add(new Suras(88, " الغاشية"));
        surasList.add(new Suras(89, " الفجر"));
        surasList.add(new Suras(90, " البلد"));
        surasList.add(new Suras(91, " الشمس"));
        surasList.add(new Suras(92, " الليل"));
        surasList.add(new Suras(93, " الضحى"));
        surasList.add(new Suras(94, " الشرح"));
        surasList.add(new Suras(95, " التين"));
        surasList.add(new Suras(96, " العلق"));
        surasList.add(new Suras(97, " القدر"));
        surasList.add(new Suras(98, " البينة"));
        surasList.add(new Suras(99, " الزلزلة"));
        surasList.add(new Suras(100, " العاديات"));
        surasList.add(new Suras(101, " القارعة"));
        surasList.add(new Suras(102, " التكاثر"));
        surasList.add(new Suras(103, " العصر"));
        surasList.add(new Suras(104, " الهمزة"));
        surasList.add(new Suras(105, " الفيل"));
        surasList.add(new Suras(106, " قريش"));
        surasList.add(new Suras(107, " الماعون"));
        surasList.add(new Suras(108, " الكوثر"));
        surasList.add(new Suras(109, " الكافرون"));
        surasList.add(new Suras(110, " النصر"));
        surasList.add(new Suras(111, " المسد"));
        surasList.add(new Suras(112, " الإخلاص"));
        surasList.add(new Suras(113, " الفلق"));
        surasList.add(new Suras(114, " النّاس"));
        return surasList;
    }

    public static List<Suras> englishSurasName() {
        List<Suras> surasList = new ArrayList<>();
        surasList.add(new Suras(1, "Al-Fatihah "));
        surasList.add(new Suras(2, "Al-Baqarah "));
        surasList.add(new Suras(3, "Al-'Imran "));
        surasList.add(new Suras(4, "An-Nisa' "));
        surasList.add(new Suras(5, "Al-Ma'idah "));
        surasList.add(new Suras(6, "Al-An'am "));
        surasList.add(new Suras(7, "Al-A'raf "));
        surasList.add(new Suras(8, "Al-Anfal "));
        surasList.add(new Suras(9, "Al-Bara'at  "));
        surasList.add(new Suras(10, "Yunus  "));
        surasList.add(new Suras(11, " Hud(Hud)"));
        surasList.add(new Suras(12, " Yusuf "));
        surasList.add(new Suras(13, "Ar - Ra'd  "));
        surasList.add(new Suras(14, "Ibrahim "));
        surasList.add(new Suras(15, " Al - Hijr "));
        surasList.add(new Suras(16, " An - Nahl "));
        surasList.add(new Suras(17, " Bani Isra'il "));
        surasList.add(new Suras(18, " Al-Kahf  "));
        surasList.add(new Suras(19, "Maryam "));
        surasList.add(new Suras(20, "Ta Ha  "));
        surasList.add(new Suras(21, "Al-Anbiya' "));
        surasList.add(new Suras(22, "Al-Hajj "));
        surasList.add(new Suras(23, "Al-Mu'minun "));
        surasList.add(new Suras(24, "An-Nur "));
        surasList.add(new Suras(25, "Al-Furqan "));
        surasList.add(new Suras(26, "Ash-Shu'ara' "));
        surasList.add(new Suras(27, "An-Naml "));
        surasList.add(new Suras(28, "Al-Qasas "));
        surasList.add(new Suras(29, "Al-'Ankabut "));
        surasList.add(new Suras(30, "Ar-Rum "));
        surasList.add(new Suras(31, "Luqman "));
        surasList.add(new Suras(32, "As-Sajdah "));
        surasList.add(new Suras(33, "Al-Ahzab "));
        surasList.add(new Suras(34, "Al-Saba'  "));
        surasList.add(new Suras(35, "Al-Fatir "));
        surasList.add(new Suras(36, "Ya Sin "));
        surasList.add(new Suras(37, "As-Saffat"));
        surasList.add(new Suras(38, "Sad "));
        surasList.add(new Suras(39, "Az-Zumar "));
        surasList.add(new Suras(40, "Al-Mu'min "));
        surasList.add(new Suras(41, "Ha Mim "));
        surasList.add(new Suras(42, "Ash-Shura "));
        surasList.add(new Suras(43, "Az-Zukhruf "));
        surasList.add(new Suras(44, "Ad-Dukhan "));
        surasList.add(new Suras(45, "Al-Jathiyah  "));
        surasList.add(new Suras(46, "Al-Ahqaf "));
        surasList.add(new Suras(47, "Muhammad "));
        surasList.add(new Suras(48, "Al-Fath "));
        surasList.add(new Suras(49, "Al-Hujurat "));
        surasList.add(new Suras(50, "Qaf  "));
        surasList.add(new Suras(51, "Ad-Dhariyat "));
        surasList.add(new Suras(52, "At-Tur "));
        surasList.add(new Suras(53, "An-Najm "));
        surasList.add(new Suras(54, "Al-Qamar "));
        surasList.add(new Suras(55, "Ar-Rahman "));
        surasList.add(new Suras(56, "Al-Waqi'ah "));
        surasList.add(new Suras(57, "Al-Hadid "));
        surasList.add(new Suras(58, "Al-Mujadilah "));
        surasList.add(new Suras(59, "Al-Hashr "));
        surasList.add(new Suras(60, "Al-Mumtahanah "));
        surasList.add(new Suras(61, "As-Saff "));
        surasList.add(new Suras(62, "Al-Jumu'ah "));
        surasList.add(new Suras(63, "Al-Munafiqun "));
        surasList.add(new Suras(64, "At-Taghabun "));
        surasList.add(new Suras(65, "At-Talaq "));
        surasList.add(new Suras(66, "At-Tahrim "));
        surasList.add(new Suras(67, "Al-Mulk "));
        surasList.add(new Suras(68, "Al-Qalam "));
        surasList.add(new Suras(69, "Al-Haqqah "));
        surasList.add(new Suras(70, "Al-Ma'arij "));
        surasList.add(new Suras(71, "Nuh  "));
        surasList.add(new Suras(72, "Al-Jinn "));
        surasList.add(new Suras(73, "Al-Muzzammil "));
        surasList.add(new Suras(74, "Al-Muddaththir "));
        surasList.add(new Suras(75, "Al-Qiyamah "));
        surasList.add(new Suras(76, "Al-Insan "));
        surasList.add(new Suras(77, "Al-Mursalat "));
        surasList.add(new Suras(78, "An-Naba'  "));
        surasList.add(new Suras(79, "An-Nazi'at "));
        surasList.add(new Suras(80, "'Abasa  "));
        surasList.add(new Suras(81, "At-Takwir "));
        surasList.add(new Suras(82, "Al-Infitar "));
        surasList.add(new Suras(83, "At-Tatfif "));
        surasList.add(new Suras(84, "Al-Inshiqaq "));
        surasList.add(new Suras(85, "Al-Buruj "));
        surasList.add(new Suras(86, "At-Tariq "));
        surasList.add(new Suras(87, "Al-A'la "));
        surasList.add(new Suras(88, "Al-Ghashiyah "));
        surasList.add(new Suras(89, "Al-Fajr "));
        surasList.add(new Suras(90, "Al-Balad "));
        surasList.add(new Suras(91, "Ash-Shams "));
        surasList.add(new Suras(92, "Al-Lail "));
        surasList.add(new Suras(93, "Ad-Duha "));
        surasList.add(new Suras(94, "Al-Inshirah "));
        surasList.add(new Suras(95, "At-Tin "));
        surasList.add(new Suras(96, "Al-'Alaq  "));
        surasList.add(new Suras(97, " Al-Qadr "));
        surasList.add(new Suras(98, " Al-Bayyinah "));
        surasList.add(new Suras(99, " Al-Zilzal  "));
        surasList.add(new Suras(100, " Al-'Adiyat "));
        surasList.add(new Suras(101, " Al-Qari'ah "));
        surasList.add(new Suras(102, "At-Takathur "));
        surasList.add(new Suras(103, "Al-'Asr "));
        surasList.add(new Suras(104, "Al-Humazah "));
        surasList.add(new Suras(105, "Al-Fil "));
        surasList.add(new Suras(106, "Al-Quraish "));
        surasList.add(new Suras(107, "Al-Ma'un  "));
        surasList.add(new Suras(108, "Al-Kauthar "));
        surasList.add(new Suras(109, "Al-Kafirun "));
        surasList.add(new Suras(110, "An-Nasr "));
        surasList.add(new Suras(111, " Al-Lahab "));
        surasList.add(new Suras(112, " Al-Ikhlas "));
        surasList.add(new Suras(113, "Al-Falaq "));
        surasList.add(new Suras(114, " An-Nas  "));
        return surasList;
    }

    public static String getLanguage() {
        return Locale.getDefault().getDisplayLanguage().equals("العربية") ? "_arabic" : "_english";
    }

    public static String getSuraIndex(int id) {
        String item;
        if (id < 9) {
            item = "00" + String.valueOf(id);
        } else if (id <= 99 && id > 9) {
            item = "0" + String.valueOf(id);
        } else {
            item = String.valueOf(id);
        }
        return item;
    }

    public static String getPlayerTitle(int suraId, String reciterName) {
        String playerTitle;
        if (SurasUtil.getLanguage().equals("_arabic")) {
            playerTitle = reciterName + " | ".concat(SurasUtil.arabicSurasName().get(suraId - 1).getName());
        } else {
            playerTitle = reciterName + " | ".concat(SurasUtil.englishSurasName().get(suraId - 1).getName());
        }
        return playerTitle;
    }

    public static String getSuraName(int suraId) {
        String suraName;
        if (SurasUtil.getLanguage().equals("_arabic")) {
            suraName = arabicSurasName().get(suraId - 1).getName();
        } else {
            suraName =englishSurasName().get(suraId - 1).getName();
        }
        return suraName;
    }

}
