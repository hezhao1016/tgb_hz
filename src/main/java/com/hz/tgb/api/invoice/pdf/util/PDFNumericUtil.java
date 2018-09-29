package com.hz.tgb.api.invoice.pdf.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by hezhao on 2018/9/18 16:16
 */
public class PDFNumericUtil {

    public static final BigDecimal DECIMAL_ZERO;
    public static final BigDecimal DECIMAL_PERCENT;
    private static final NumberFormat usFormat;

    static {
        DECIMAL_ZERO = BigDecimal.ZERO;
        DECIMAL_PERCENT = (new BigDecimal(100)).setScale(0, 4);
        usFormat = NumberFormat.getNumberInstance(Locale.US);
    }

    public PDFNumericUtil() {
    }

    public static BigDecimal multiply(BigDecimal faciend, BigDecimal multiplier) {
        faciend = faciend == null ? BigDecimal.ZERO : faciend;
        multiplier = multiplier == null ? BigDecimal.ZERO : multiplier;
        return faciend.multiply(multiplier).setScale(2, 4);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        dividend = dividend == null ? BigDecimal.ZERO : dividend;
        divisor = divisor == null ? BigDecimal.ONE : divisor;
        return dividend.divide(divisor, 2, 4);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale) {
        dividend = dividend == null ? BigDecimal.ZERO : dividend;
        divisor = divisor == null ? BigDecimal.ONE : divisor;
        return dividend.divide(divisor, scale, 4);
    }

    public static BigDecimal add(BigDecimal origin, BigDecimal augend) {
        origin = origin == null ? BigDecimal.ZERO : origin;
        augend = augend == null ? BigDecimal.ZERO : augend;
        return origin.add(augend).setScale(2, 4);
    }

    public static BigDecimal add(String originStr, String augendStr) {
        BigDecimal origin = BigDecimal.ZERO;
        BigDecimal augend = BigDecimal.ZERO;
        if (PDFNumberValidationUtils.isRealNumber(originStr)) {
            origin = new BigDecimal(originStr);
        }

        if (PDFNumberValidationUtils.isRealNumber(augendStr)) {
            augend = new BigDecimal(augendStr);
        }

        return origin.add(augend).setScale(2, 4);
    }

    public static BigDecimal addSum(List<BigDecimal> list) {
        if (list == null) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal sum = BigDecimal.ZERO;

            BigDecimal bigDecimal;
            for(Iterator var3 = list.iterator(); var3.hasNext(); sum = add(sum, bigDecimal)) {
                bigDecimal = (BigDecimal)var3.next();
            }

            return sum;
        }
    }

    public static BigDecimal addSum(BigDecimal... bigDecimalArr) {
        if (bigDecimalArr == null) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal sum = BigDecimal.ZERO;
            BigDecimal[] var5 = bigDecimalArr;
            int var4 = bigDecimalArr.length;

            for(int var3 = 0; var3 < var4; ++var3) {
                BigDecimal bigDecimal = var5[var3];
                sum = add(sum, bigDecimal);
            }

            return sum;
        }
    }

    public static BigDecimal substract(BigDecimal subtrahend, BigDecimal origin) {
        origin = origin == null ? BigDecimal.ZERO : origin;
        subtrahend = subtrahend == null ? BigDecimal.ZERO : subtrahend;
        return subtrahend.subtract(origin).setScale(2, 4);
    }

    public static String toString(BigDecimal numeric) {
        return numeric == null ? "0" : numeric.setScale(2, 4).toPlainString();
    }

    public static String toString(int scale, BigDecimal numeric) {
        return numeric == null ? "0" : numeric.setScale(scale, 4).toPlainString();
    }

    public static String toFenString(BigDecimal numeric) {
        return numeric != null && numeric.compareTo(BigDecimal.ZERO) != 0 ? toString(2, numeric).replaceAll("\\.", "") : "";
    }

    public static String formatPrice(BigDecimal origin) {
        return origin == null ? null : usFormat.format(origin);
    }

    public static String formatPrice(String origin) {
        if (origin == null) {
            return null;
        } else {
            BigDecimal orgDec = null;

            try {
                orgDec = new BigDecimal(origin);
            } catch (Exception var3) {
                var3.printStackTrace();
                return origin;
            }

            return usFormat.format(orgDec);
        }
    }

    public static String changeNumAmount(BigDecimal data) {
        if (data == null) {
            data = BigDecimal.ZERO;
        }

        String dataFirst = formatPrice(data);
        if (dataFirst.equals("0")) {
            return "0.00";
        } else if (dataFirst.indexOf(".") == -1) {
            return dataFirst + ".00";
        } else {
            String[] debitStr = dataFirst.toString().split("\\.");
            if (debitStr[1].length() == 1) {
                return dataFirst + "0";
            } else {
                return debitStr[1].length() == 2 ? dataFirst : dataFirst;
            }
        }
    }

    public static String changeNumAmount(String dataStr) {
        if (dataStr == null) {
            return "";
        } else {
            dataStr = dataStr.replaceAll(",", "");
            if (!PDFNumberValidationUtils.isRealNumber(dataStr)) {
                return "";
            } else {
                BigDecimal data = new BigDecimal(dataStr);
                String dataFirst = formatPrice(data);
                if (dataFirst.equals("0")) {
                    return "0.00";
                } else if (dataFirst.indexOf(".") == -1) {
                    return dataFirst + ".00";
                } else {
                    String[] debitStr = dataFirst.toString().split("\\.");
                    if (debitStr[1].length() == 1) {
                        return dataFirst + "0";
                    } else {
                        return debitStr[1].length() == 2 ? dataFirst : dataFirst;
                    }
                }
            }
        }
    }

    public static BigDecimal changeStrToNumFormat(String dataStr) {
        if (dataStr != null && !"".equals(dataStr)) {
            dataStr = dataStr.trim().replace(" ", "");
            String[] arr = dataStr.split(",");
            String strArr = "";
            String[] var6 = arr;
            int var5 = arr.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                String str = var6[var4];
                strArr = strArr + str;
            }

            if (!PDFNumberValidationUtils.isRealNumber(strArr)) {
                return BigDecimal.ZERO;
            } else {
                try {
                    return new BigDecimal(strArr);
                } catch (Exception var7) {
                    var7.printStackTrace();
                    return BigDecimal.ZERO;
                }
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static String changeStrToNumStr(String dataStr) {
        return dataStr != null && !"".equals(dataStr) ? dataStr.trim().replaceAll(",", "") : "";
    }

    public static BigDecimal changeBigDecimalRemoveNull(BigDecimal index) {
        if (index == null) {
            index = BigDecimal.ZERO;
        }

        return index;
    }

    public static int getMax(int[] arr) {
        int max = arr[0];

        for(int i = 1; i < arr.length; ++i) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }

    public static BigDecimal getMax(List<BigDecimal> list) {
        if (list == null) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal max = (BigDecimal)list.get(0);

            for(int i = 1; i < list.size(); ++i) {
                if (((BigDecimal)list.get(i)).compareTo(max) == 1) {
                    max = (BigDecimal)list.get(i);
                }
            }

            BigDecimal setScale = max.setScale(2, 5);
            return setScale;
        }
    }

    public static BigDecimal getMax(BigDecimal... arr) {
        BigDecimal max = arr[0];

        for(int i = 1; i < arr.length; ++i) {
            if (arr[i].compareTo(max) == 1) {
                max = arr[i];
            }
        }

        BigDecimal setScale = max.setScale(2, 5);
        return setScale;
    }

    public static BigDecimal getMax2(BigDecimal... arr) {
        BigDecimal max = arr[0];

        for(int i = 1; i < arr.length; ++i) {
            if (arr[i].compareTo(max) == 1) {
                max = arr[i];
            }
        }

        BigDecimal setScale = max.setScale(2, 5);
        return setScale;
    }

    public static BigDecimal returnNoNegativeNumber(BigDecimal count) {
        return count.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : count;
    }

    public static boolean isZeroOrIsBlank(BigDecimal count) {
        boolean flag = false;
        if (count == null || count.compareTo(BigDecimal.ZERO) == 0) {
            flag = true;
        }

        return flag;
    }

    public static void main(String[] args) {
    }

}
