package cn.acyou.gemini.constant;

import com.google.common.base.CharMatcher;

/**
 * @author youfang
 * @date 2018-04-15 下午 09:42
 **/
public class GaminiConstant {

    public static final int SUCCESS = 200;
    public static final String MESSAGE = "请求成功！";

    public static final String SHORT_DATE_PATTERN = "yyyyMMdd";
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String SPECIFIC_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SERIES_FORMAT_PATTERN = "yyyyMMddHHmmss";


    /**
     * 过滤掉31以下的ascii码以及双引号和反斜扛
     */
    public static final CharMatcher DANGER_CHAR_MATCHER = CharMatcher.inRange('\0', '\u001F')
            .or(CharMatcher.is('"'))
            .or(CharMatcher.is('\\'));

    public static final String SECURITIES_TIMES_HS_A = "securities_times_hs_a_";

    public static final String MUTUAL_MARKET_SH = "mutual_market_sh_";
    public static final String MUTUAL_MARKET_SZ = "mutual_market_sz_";



}
