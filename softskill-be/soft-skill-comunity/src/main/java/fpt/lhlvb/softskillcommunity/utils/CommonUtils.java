package fpt.lhlvb.softskillcommunity.utils;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static SecretKeySpec secretKey;
    public static byte[] key;
    public static String ALGORITHM = "AES";

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYYMMDD);

    public static String randomString(int length) {
        SecureRandom rnd = new SecureRandom();
        String randomString = CommonConstant.RANDOM_STRING;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(randomString.charAt(rnd.nextInt(randomString.length())));
        return sb.toString();
    }

    public static int getEndDateInMonthYear(String[] arrMonthYear) {
        if (arrMonthYear.length < 2) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.set(Calendar.MONTH, Integer.parseInt(arrMonthYear[1]) - 1);
            calendar.set(Calendar.YEAR, Integer.parseInt(arrMonthYear[0]));
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Timestamp stringToTimestamp(String strTime) {
        try {
            DateFormat formatter = new SimpleDateFormat(CommonConstant.TIMESTAMP_FORMAT);
            Date date = formatter.parse(strTime);
            Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate;
        } catch (Exception e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    public static Date getDateNow() {
        Date date = null;
        try {
            date = simpleDateFormat.parse(simpleDateFormat.format(CommonUtils.resultTimestamp().getTime()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static List<Date> getStartAndEndDateInMonth(int month, int year) {
        List<Date> result = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        result.add(calendar.getTime());

        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        result.add(calendar.getTime());

        return result;
    }

    public static Map<String, Object> compareTwoDate(Date fromDate, Date toDate) {
        Map<String, Object> result = new HashMap<>();

        LocalDate fromDateLocal = Instant.ofEpochMilli(fromDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate toDateLocal = Instant.ofEpochMilli(toDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Period diff = Period.between(fromDateLocal, toDateLocal);

        result.put("year", diff.getYears());
        result.put("month", diff.getMonths());
        result.put("day", diff.getDays());
        return result;
    }

    public static void prepareSecreteKey(String myKey) {
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest();
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.info("Error while encrypting: " + e.getMessage());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            logger.info("Error while decrypting: {}", e.getMessage());
        }
        return null;
    }

    public static LocalDateTime toCommonDate(String datetime) {
        if (StringUtils.isEmpty(datetime)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT_YYYYMMDD_HHMM);
            return LocalDateTime.parse(datetime, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * convert string to LocalDateTime
     *
     * @param datetime
     * @param formatString
     * @return LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String datetime, String formatString) {
        if (StringUtils.isEmpty(datetime)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
            return LocalDateTime.parse(datetime, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * convert string to LocalDate
     *
     * @param datetime
     * @param formatString
     * @return LocalDate
     */
    public static LocalDate stringToLocalDate(String datetime, String formatString) {
        if (StringUtils.isEmpty(datetime)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
            return LocalDate.parse(datetime, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <p>説明 :  LocalDateTime to string</p>
     *
     * @param date   LocalDateTime
     * @param format format date
     * @return String str date format
     */
    public static String localDateTimeToString(LocalDateTime date, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return date.format(formatter);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * <p>説明 :  LocalDate to string</p>
     *
     * @param date   LocalDate
     * @param format format date
     * @return String str date format
     */
    public static String localDateToString(LocalDate date, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return date.format(formatter);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * String to Integer
     *
     * @param str String
     * @return Integer
     */
    public static Integer toInt(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        try {
            str = removeComma(str);
            return Integer.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * String to long
     *
     * @param str String
     * @return Long
     */
    public static Long toLong(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        try {
            str = removeComma(str);
            return Long.valueOf(str);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * String to Double
     *
     * @param str
     * @return Double
     */
    public static Double toDouble(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0D;
        }

        try {
            str = removeComma(str);
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0D;
        }
    }

    public static String formatNumberWithCommas(Integer value) {
        try {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(value);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    public static String formatNumberWithCommas(Long value) {
        try {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(value);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    public static String formatNumberWithCommas(double value) {
        try {
            DecimalFormat formatter = new DecimalFormat("#0.00");
            return formatter.format(value);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * <p>説明 :  convert money format</p>
     *
     * @return str money format
     */
    public static String toMoneyFormat(String str) {
        if (StringUtils.isEmpty(str)) {
            return StringUtils.EMPTY;
        } else {
            double amount = Double.parseDouble(str);
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            return formatter.format(amount);
        }
    }

    /**
     * <p>説明 : データ設定メソッドを呼び出す</p>
     *
     * @param targetObj  Target Object
     * @param paramObj   Param Object
     * @param methodName method Name
     */
    public static void invokeSetMethod(Object targetObj, Object paramObj, String methodName, Class<?>... target) {
        try {
            if (paramObj != null) {
                Method setMethod = targetObj.getClass().getMethod(methodName, paramObj instanceof ArrayList ? List.class : paramObj.getClass());
                setMethod.invoke(targetObj, paramObj);
            } else {
                Method method = targetObj.getClass().getMethod(methodName, target);
                method.invoke(targetObj, paramObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 入力文字列は数字であるかのチェック
     *
     * @param number 文字列
     * @return true: 　入力文字列は数字　　 false: 入力文字列は数字以外
     */
    public static boolean isSignNumber(String number) {
        if (StringUtils.isEmpty(number)) {
            return true;
        }

        Pattern p = Pattern.compile("^[-]?[0-9]+$");
        Matcher m = p.matcher(number);

        return m.find();
    }

    public static boolean isNumberOrDecimal(String number) {
        if (StringUtils.isEmpty(number)) {
            return true;
        }

        // Pattern p = Pattern.compile("^[1-9]\\d*(\\.\\d+)?$");
        Pattern p = Pattern.compile("^\\d*(\\.\\d+)?$");
        Matcher m = p.matcher(number);

        return m.find();
    }

    /**
     * 日付フォーアットをチェック
     *
     * @param dateString   Date string value
     * @param formatString Format date
     * @return boolean
     * true : 有効の日付フォーマットである場合
     * false : 無効の日付フォーマットである場合
     */
    public static boolean isDateFormat(String dateString, String formatString) {
        if (StringUtils.isEmpty(dateString)) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
        try {
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * String to long
     *
     * @param str
     * @return Long
     */
    public static Long toLongHasNull(String str) {
        try {
            if (str != null && str.length() > 0) {
                return Long.valueOf(str);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String to Integer
     *
     * @param str
     * @return Integer
     */
    public static Integer toIntHasNull(String str) {
        try {
            if (str != null && str.length() > 0) {
                return Integer.valueOf(str);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>説明 : 新しいモデルマッパーの作成</p>
     *
     * @return ModelMapper
     * @author : minh.ls
     */
    public static ModelMapper createNewModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setPropertyCondition(new Condition<Object, Object>() {
            public boolean applies(MappingContext<Object, Object> pContext) {
                if (null == pContext.getSource() || null == pContext.getDestinationType()) {
                    return false;
                }

                if (pContext.getSourceType().equals(String.class)
                        && (Integer.class.equals(pContext.getDestinationType()) || int.class.equals(pContext.getDestinationType()))) {
                    return isSignNumber(pContext.getSource().toString());
                }
                return true;
            }
        });

        // List<T> -> List<T>に変換
//        Converter<List<T>, List<T>> listToList = new Converter<List<T>, List<T>>() {
//            @Override
//            public List<T> convert(MappingContext<List<T>, List<T>> context) {
//                List<T> source = context.getSource();
//                if (source == null || source.size() == 0) {
//                    return new ArrayList<T>();
//                }
//
//                TypeMap<List<T>, List<T>> typeMap = modelMapper.createTypeMap(context.getSourceType(), context.getDestinationType());
//                return typeMap.map(context.getSource());
//            }
//        };
//        modelMapper.addConverter(listToList);

        // List<String> -> Stringに変換
        Converter<List<String>, String> stringListToString = new Converter<List<String>, String>() {
            @Override
            public String convert(MappingContext<List<String>, String> context) {
                List<String> source = context.getSource();
                if (source == null || source.size() == 0) {
                    return StringUtils.EMPTY;
                }
                return String.join(CommonConstant.COMMA, source);
            }
        };
        modelMapper.addConverter(stringListToString);

        // String -> List<String>に変換
        Converter<String, List<String>> stringToStringList = new Converter<String, List<String>>() {
            @Override
            public List<String> convert(MappingContext<String, List<String>> context) {
                String source = context.getSource();
                if (StringUtils.isEmpty(source)) {
                    return new ArrayList<String>();
                }
                return Arrays.asList(source.split(CommonConstant.COMMA));
            }
        };
        modelMapper.addConverter(stringToStringList);

        // String -> LocalDateに変換
        Converter<String, LocalDate> localDateConverter = new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> context) {
                String source = context.getSource();
                if (StringUtils.isEmpty(source) || !isDateFormat(source, CommonConstant.DATE_FORMAT_DDMMYYYY)) {
                    return null;
                }
                return stringToLocalDate(source, CommonConstant.DATE_FORMAT_DDMMYYYY);
            }
        };
        modelMapper.addConverter(localDateConverter);

        // LocalDate -> Stringに変換
        Converter<LocalDate, String> stringConverter = new Converter<LocalDate, String>() {
            @Override
            public String convert(MappingContext<LocalDate, String> context) {
                LocalDate source = context.getSource();
                if (StringUtils.isEmpty(source.toString())) {
                    return null;
                }

                return localDateToString(source, CommonConstant.DATE_FORMAT_DDMMYYYY);
            }
        };
        modelMapper.addConverter(stringConverter);

        // String -> LocalDateTimeに変換
        Converter<String, LocalDateTime> localDateTimeConverter = new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(MappingContext<String, LocalDateTime> context) {
                String source = context.getSource();
                if (StringUtils.isEmpty(source) || !isDateFormat(source, CommonConstant.LOCAL_DATE_TIME_FORMAT)) {
                    return null;
                }
                return stringToLocalDateTime(source, CommonConstant.LOCAL_DATE_TIME_FORMAT);
            }

        };
        modelMapper.addConverter(localDateTimeConverter);

        // LocalDateTime -> Stringに変換
        Converter<LocalDateTime, String> stringTimeConverter = new Converter<LocalDateTime, String>() {
            @Override
            public String convert(MappingContext<LocalDateTime, String> context) {
                LocalDateTime source = context.getSource();
                if (StringUtils.isEmpty(source.toString())) {
                    return null;
                }
                return localDateTimeToString(source, CommonConstant.LOCAL_DATE_TIME_FORMAT);
            }
        };
        modelMapper.addConverter(stringTimeConverter);

        // Long -> Stringに変換
        Converter<Long, String> longStringConverter = new Converter<Long, String>() {
            @Override
            public String convert(MappingContext<Long, String> context) {
                Long source = context.getSource();
                if (source == null) {
                    return null;
                }
                return String.valueOf(source);
            }
        };
        modelMapper.addConverter(longStringConverter);

        // String -> Longに変換
        Converter<String, Long> stringLongConverter = new Converter<String, Long>() {
            @Override
            public Long convert(MappingContext<String, Long> context) {
                String source = removeComma(context.getSource());
                if (StringUtils.isEmpty(source)) {
                    return null;
                }
                return toLongHasNull(source);
            }
        };
        modelMapper.addConverter(stringLongConverter);

        // Integer -> Stringに変換
        Converter<Integer, String> integerStringConverter = new Converter<Integer, String>() {
            @Override
            public String convert(MappingContext<Integer, String> context) {
                Integer source = context.getSource();
                if (source == null) {
                    return null;
                }
                return String.valueOf(source);
            }
        };
        modelMapper.addConverter(integerStringConverter);

        // String -> Integerに変換
        Converter<String, Integer> stringIntegerConverter = new Converter<String, Integer>() {
            @Override
            public Integer convert(MappingContext<String, Integer> context) {
                String source = removeComma(context.getSource());
                if (StringUtils.isEmpty(source)) {
                    return null;
                }
                return toIntHasNull(source);
            }
        };
        modelMapper.addConverter(stringIntegerConverter);

        // Boolean -> Stringに変換
        Converter<Boolean, String> booleanSringConverter = new Converter<Boolean, String>() {
            @Override
            public String convert(MappingContext<Boolean, String> context) {
                Boolean source = context.getSource();
                if (BooleanUtils.isTrue(source)) {
                    return CommonConstant.TRUE_FLG;
                } else {
                    return CommonConstant.FALSE_FLG;
                }
            }
        };
        modelMapper.addConverter(booleanSringConverter);

        // Boolean -> Stringに変換
        Converter<String, Boolean> stringBooleanConverter = new Converter<String, Boolean>() {
            @Override
            public Boolean convert(MappingContext<String, Boolean> context) {
                String source = context.getSource();
                if (StringUtils.isBlank(source)) {
                    return Boolean.FALSE;
                } else if (CommonConstant.FALSE_FLG.equals(source)) {
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }
        };
        modelMapper.addConverter(stringBooleanConverter);
        return modelMapper;
    }

    /**
     * remove all comma
     *
     * @param str
     * @return
     */
    public static String removeComma(String str) {
        if (str != null) {
            return str.replaceAll(CommonConstant.COMMA, StringUtils.EMPTY);
        }
        return null;
    }

    /**
     * get first date from month
     *
     * @param monthStr
     * @return
     */
    public static LocalDate getFirstDayOfMonth(String monthStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT_DDMMYYYY);
        LocalDate date = LocalDate.parse("01/" + monthStr, formatter);
        return date.withDayOfMonth(1);
    }

    /**
     * get last date from month
     *
     * @param monthStr
     * @return
     */
    public static LocalDate getLastDayOfMonth(String monthStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT_DDMMYYYY);
        LocalDate date = LocalDate.parse("01/" + monthStr, formatter);
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    /**
     * get first date from month
     *
     * @param monthStr
     * @return
     */
    public static String getFirstDayOfMonthStr(String monthStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT_DDMMYYYY);
        LocalDate date = LocalDate.parse("01/" + monthStr, formatter);
        return localDateToString(date.withDayOfMonth(1), CommonConstant.DATE_FORMAT_DDMMYYYY);
    }

    /**
     * get last date from month
     *
     * @param monthStr
     * @return
     */
    public static String getLastDayOfMonthStr(String monthStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT_DDMMYYYY);
        LocalDate date = LocalDate.parse("01/" + monthStr, formatter);
        return localDateToString(date.withDayOfMonth(date.lengthOfMonth()), CommonConstant.DATE_FORMAT_DDMMYYYY);
    }

    /**
     * format money
     *
     * @param number
     * @return
     */
    public static String moneyFormat(String number) {
        try {
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,###.##");
            return formatter.format(amount);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * String to Float
     *
     * @return Float
     */
    public static Float toFloatWithComma(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0F;
        }

        try {
            str = removeComma(str);
            return Float.parseFloat(str);
        } catch (Exception e) {
            return 0F;
        }
    }

    /**
     * convert to big decimal value
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            return new BigDecimal(0);
        }
    }

    /**
     * is password expired
     *
     * @param resetPasswordSentAt
     * @param minutes
     * @return
     */
    public static Boolean isPasswordExpired(Date resetPasswordSentAt, int minutes) {
        Calendar expiredToken = Calendar.getInstance();
        expiredToken.setTime(resetPasswordSentAt);
        expiredToken.add(Calendar.MINUTE, minutes);
        return new Date().after(expiredToken.getTime());
    }

    /**
     * @param price
     */
    public static String getPriceFormat(Long price) {
        NumberFormat nf = DecimalFormat.getInstance(Locale.ENGLISH);
        DecimalFormat decimalFormatter = (DecimalFormat) nf;
        decimalFormatter.applyPattern("#,###,###.##");
        return decimalFormatter.format(price);
    }

    public static Timestamp resultTimestamp() {
        // HoanNNC update common add zone JST
        LocalDateTime localDateTimeJST = Instant.now().atOffset(ZoneOffset.of("+07:00")).toLocalDateTime();
        return Timestamp.valueOf(localDateTimeJST);
    }

    public static String calculateTimeStamp(Timestamp now, Timestamp timestamp) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        Long days = TimeUnit.MILLISECONDS.toDays(now.getTime()) - TimeUnit.MILLISECONDS.toDays(timestamp.getTime());
        if (days.equals(0L)) {
            return "Hôm nay, " + sdf1.format(timestamp);
        } else if (days.equals(1L)) {
            return "Hôm qua, " + sdf1.format(timestamp);
        }
        return sdf2.format(timestamp);
    }
}