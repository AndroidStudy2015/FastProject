package com.fast.ui.fast_ui.indexview.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by apple on 2017/8/10.
 */

public class ComparatorUtils {


    /**
     * 将所有的城市名字按照全拼音排序
     *
     * @return
     */
    public static List<String> sortCityList(List<String> cityStrings) {

        List<City> cityList = new ArrayList<>();


        for (int i = 0; i < cityStrings.size(); i++) {
            String cityName = cityStrings.get(i);
            String cityQuanPin = PinYinUtils.getCityQuanPin(cityName);
            cityList.add(new City(cityName, cityQuanPin));
        }

        Collections.sort(cityList, new Comparator<City>() {

            @Override
            public int compare(City c1, City c2) {
                return c1.getPinyin().compareTo(c2.getPinyin());
            }
        });
        List<String> sortCityList = new ArrayList<>();


        for (int i = 0; i < cityList.size(); i++) {
            sortCityList.add(cityList.get(i).getName());
        }

//        ★ 把"#"开头的城市都移动到整个list的最后
//        如果排序后城市列表第一位拼音首字母是"#"，把所有"#"的城市都排在最后
        if ("#".equals(PinYinUtils.getPinYinFirstLetter(sortCityList.get(0)))) {

//            找到列表中，以A字母开头的位置
            int positionFirstHanZi = 0;
            for (int i = 0; i < sortCityList.size(); i++) {
                String pinYinFirstLetter = PinYinUtils.getPinYinFirstLetter(sortCityList.get(i));
                if (!("#".equals(pinYinFirstLetter))) {
                    positionFirstHanZi = i;
                    break;
                }
            }

            List<String> isNotHanZiList = sortCityList.subList(0, positionFirstHanZi);
            List<String> hanZiList = sortCityList.subList(positionFirstHanZi, sortCityList.size());
            List<String> reSortCityList = new ArrayList<>();
            reSortCityList.addAll(hanZiList);
            reSortCityList.addAll(isNotHanZiList);

            return reSortCityList;
        }




        return sortCityList;
    }


    /**
     * 传入按字母排序号的城市列表，生成一串以城市列表首字母组成的字符串
     * 例如：AAAAAABBBBBBCCCCCDDDDDDDDEEEEEFFFFFZZZZZ
     *
     * @param cityData
     * @return
     */
    public static String getSortCityListFirstLetterToString(List<String> cityData) {

        String s = "";
        for (int i = 0; i < cityData.size(); i++) {
            String city = cityData.get(i);
            String pinYinFirstLetter = PinYinUtils.getPinYinFirstLetter(city);
            s = s + pinYinFirstLetter;
        }

        return s;

    }


    /**
     * @param list
     * @param comparator 比较是否为同一组的比较器，此方法排序慢，已经不用了，但是先留在这里吧
     * @return
     */
    public static <T> List<List<T>> dividerList(List<T> list, Comparator<? super T> comparator) {
        List<List<T>> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            boolean isContain = false;
            for (int j = 0; j < lists.size(); j++) {
                if (lists.get(j).size() == 0
                        || comparator.compare(lists.get(j).get(0), list.get(i)) == 0) {
                    lists.get(j).add(list.get(i));
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                List<T> newList = new ArrayList<>();
                newList.add(list.get(i));
                lists.add(newList);
            }
        }
        return lists;
    }


    /**
     * 将一个cityList按照首字母分组 此方法排序慢，已经不用了，但是先留在这里吧
     *
     * @param list
     * @return
     */
    public static List<List<String>> byFirstLetter(List<String> list) {

        List<List<String>> byFirstLetterList = dividerList(list, new Comparator<String>() {
            @Override
            public int compare(String c1, String c2) {
                return PinYinUtils.getPinYinFirstLetter(c1).compareTo(PinYinUtils.getPinYinFirstLetter(c2));

            }
        });


        Log.e("qaa", byFirstLetterList.get(0).size() + "qqqqqqqqqq" + byFirstLetterList);
        return byFirstLetterList;
    }


}
