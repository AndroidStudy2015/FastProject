package com.fast.ui.fast_ui.indexview.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * Created by apple on 2017/8/10.
 */

public class PinYinUtils {

    /**
     * 得到城市名字的大写全拼音
     * 例如："北京"返回"BEIJING"
     * @param cityName
     * @return
     */
    public static String getCityQuanPin(String cityName) {
//      这里处理常见多音字
        if (cityName.contains("重庆")){
            return "CHONGQING";
        }



        String pinyin = "";
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

//        for (int j = 0; j < 2; j++) {
        for (int j = 0; j < cityName.length(); j++) {
            char c = cityName.charAt(j);

            if (!isChinese(c)){
                pinyin+=c+"";
            }else {
                String[] vals;
                try {
                    vals = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    pinyin += vals[0];
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }
        }

//        Log.e("qwe", pinyin);

         pinyin = pinyin.toUpperCase();
        char firstLetterTempPinyin = pinyin.charAt(0);
//        首字母不是A~Z的话，在拼音前方家一个'#'
        if ((firstLetterTempPinyin<'A')||(firstLetterTempPinyin>'Z')){
            pinyin='#'+pinyin;
        }
        return pinyin;
    }

    /**
     * 得到一个城市的首个汉字拼音的大写首字母
     * 例如："北京"，返回"B"
     * @param cityName
     * @return
     */

    public static String getPinYinFirstLetter(String cityName) {


        String cityQuanPin = getCityQuanPin(cityName);

        String firstLetter = cityQuanPin.substring(0, 1);
        return firstLetter;

    }
    public static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
