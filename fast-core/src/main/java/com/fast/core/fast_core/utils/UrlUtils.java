package com.fast.core.fast_core.utils;

/**
 * Created by a on 2016/11/6.
 */

public class UrlUtils {
//          public static final String BASE_URL = "http://192.168.252.118:8080/bkdy/";//原来内网，不可用
//          public static final String BASE_URL = "http://testapi.baikanmovie.com:8080/";//公测环境
    public static final String BASE_URL = "https://www.baikanmovie.com/";//外网


    /**
     * 首页-热门影片
     */
    public static final String URL_HOME_PAGER = "front/home!getMovieList.do";

    //正在热映   ★这里最前面不能带“/”(不带/是直接拼接，带/是拼接baseurl的主机部分，你的baseurl恰好是只包含主机部分就无所谓了)
    public static final String URL_GET_HOT_SHOW = "front/cinema!getCinemaHotList.do";
    //即将上映
    public static final String URL_GET_WILL_SHOW =  "front/cinema!getWillShow.do";
    //影片详情
    public static final String URL_MOVIE_DETAIL =  "front/cinema!getMovieDetail.do";
    //城市列表
    public static final String URL_GET_CITY_LIST = "front/user!getCityList.do";
    //区域
    public static final String URL_GET_AREA_AND_FEATURE = "front/cinema!getAreaAndFeature.do";

    // TODO: 2017/10/19 记得找Mr王换链接
    //通过影片查询影院
//    public static final String URL_GET_MOVIE_CINEMA_LIST_BY_MOVIEID = "front/movie!getShowMovieCinemaListByMovieId.do";
    public static final String URL_GET_MOVIE_CINEMA_LIST_BY_MOVIEID = "http://testapi.baikanmovie.com/front/movie!getShowMovieCinemaListByMovieId.do";

    //影片在某一个影院的排期
    public static final String URL_GET_MOVIE_IN_CINEMA_SHOW_LIST = "front/cinema!getMovieInCinemaShowList.do";

//    http://www.baikanmovie.com/front/cinema!getCinemaHotList.do?para.userId=1113&para.size=999
//    http://testapi.baikanmovie.com:8080/front/cinema!getWillShow.do?para.userId=1113&para.size=999
}
