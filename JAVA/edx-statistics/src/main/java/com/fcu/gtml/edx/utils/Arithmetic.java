package com.fcu.gtml.edx.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Arithmetic {

    private BigDecimal value;

    public Arithmetic(int value) {
        this.value = new BigDecimal(Integer.toString(value));
    }

    public Arithmetic(long value) {
        this.value = new BigDecimal(Long.toString(value));
    }

    public Arithmetic(float value) {
        checkValue(value);
        this.value = new BigDecimal(Float.toString(value));
    }

    public Arithmetic(double value) {
        checkValue(value);
        this.value = new BigDecimal(Double.toString(value));
    }

    public Arithmetic add(int val) {
        this.value = this.value.add(new BigDecimal(Integer.toString(val)));
        return this;
    }

    public Arithmetic add(long val) {
        this.value = this.value.add(new BigDecimal(Long.toString(val)));
        return this;
    }

    public Arithmetic add(float val) {
        checkValue(val);
        this.value = this.value.add(new BigDecimal(Float.toString(val)));
        return this;
    }

    public Arithmetic add(double val) {
        checkValue(val);
        this.value = this.value.add(new BigDecimal(Double.toString(val)));
        return this;
    }

    public Arithmetic sub(int val) {
        this.value = this.value.subtract(new BigDecimal(Integer.toString(val)));
        return this;
    }

    public Arithmetic sub(long val) {
        this.value = this.value.subtract(new BigDecimal(Long.toString(val)));
        return this;
    }

    public Arithmetic sub(float val) {
        checkValue(val);
        this.value = this.value.subtract(new BigDecimal(Float.toString(val)));
        return this;
    }

    public Arithmetic sub(double val) {
        checkValue(val);
        this.value = this.value.subtract(new BigDecimal(Double.toString(val)));
        return this;
    }

    public Arithmetic mul(int val) {
        this.value = this.value.multiply(new BigDecimal(Integer.toString(val)));
        return this;
    }

    public Arithmetic mul(long val) {
        this.value = this.value.multiply(new BigDecimal(Long.toString(val)));
        return this;
    }

    public Arithmetic mul(float val) {
        checkValue(val);
        this.value = this.value.multiply(new BigDecimal(Float.toString(val)));
        return this;
    }

    public Arithmetic mul(double val) {
        checkValue(val);
        this.value = this.value.multiply(new BigDecimal(Double.toString(val)));
        return this;
    }

    public Arithmetic div(int val) {
        this.value = this.value.divide(new BigDecimal(Integer.toString(val)));
        return this;
    }

    public Arithmetic div(long val) {
        this.value = this.value.divide(new BigDecimal(Long.toString(val)));
        return this;
    }

    public Arithmetic div(float val) {
        checkValue(val);
        this.value = this.value.divide(new BigDecimal(Float.toString(val)));
        return this;
    }

    public Arithmetic div(double val) {
        checkValue(val);
        this.value = this.value.divide(new BigDecimal(Double.toString(val)));
        return this;
    }

    public Arithmetic div(int val, int scale) {
        this.value = this.value.divide(new BigDecimal(Integer.toString(val)), scale, BigDecimal.ROUND_HALF_UP);
        return this;
    }

    public Arithmetic div(long val, int scale) {
        this.value = this.value.divide(new BigDecimal(Long.toString(val)), scale, BigDecimal.ROUND_HALF_UP);
        return this;
    }

    public Arithmetic div(float val, int scale) {
        checkValue(val);
        this.value = this.value.divide(new BigDecimal(Float.toString(val)), scale, BigDecimal.ROUND_HALF_UP);
        return this;
    }

    public Arithmetic div(double val, int scale) {
        checkValue(val);
        this.value = this.value.divide(new BigDecimal(Double.toString(val)), scale, BigDecimal.ROUND_HALF_UP);
        return this;
    }

    private void checkValue(double value) {
        if (Double.isInfinite(value))
            throw new ArithmeticException("value is infinite.");
        if (Double.isNaN(value))
            throw new ArithmeticException("value is Nan.");
    }

    private void checkValue(float value) {
        if (Float.isInfinite(value))
            throw new ArithmeticException("value is infinite.");
        if (Float.isNaN(value))
            throw new ArithmeticException("value is Nan.");
    }

    public int intValue() {
        return this.value.intValueExact();
    }

    public long longValue() {
        return value.longValue();
    }

    public float floatValue() {
        return this.value.floatValue();
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    public BigDecimal toBigDecimal() {
        return this.value;
    }

    public int compareTo(int val) {
        return compareTo(new BigDecimal(Integer.toString(val)));
    }

    public int compareTo(long val) {
        return compareTo(new BigDecimal(Long.toString(val)));
    }

    public int compareTo(float val) {
        return compareTo(new BigDecimal(Float.toString(val)));
    }

    public int compareTo(double val) {
        return compareTo(new BigDecimal(Double.toString(val)));
    }

    public int compareTo(Arithmetic val) {
        return this.compareTo(val.value);
    }

    public int compareTo(BigDecimal val) {
        return this.value.compareTo(val);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    // ---------------------------------------------------

    /**
     * 提供精確的加法運算。
     * 
     * @param augend
     *            被加數
     * @param addend
     *            加數
     * @return 兩個參數的和
     */
    public static double add(double augend, double addend) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(augend));
            BigDecimal b2 = new BigDecimal(Double.toString(addend));
            return b1.add(b2).doubleValue();
        } catch (Exception e) {
            if (Double.isInfinite(augend))
                throw new ArithmeticException("augend is infinite.");
            if (Double.isNaN(augend))
                throw new ArithmeticException("augend is Nan.");
            if (Double.isInfinite(addend))
                throw new ArithmeticException("addend is infinite.");
            if (Double.isNaN(addend))
                throw new ArithmeticException("addend is Nan.");
            throw e;
        }
    }

    /**
     * 提供精確的減法運算。
     * 
     * @param minuend
     *            被減數
     * @param subtrahend
     *            減數
     * @return 兩個參數的差
     */
    public static double sub(double minuend, double subtrahend) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(minuend));
            BigDecimal b2 = new BigDecimal(Double.toString(subtrahend));
            return b1.subtract(b2).doubleValue();
        } catch (Exception e) {
            if (Double.isInfinite(minuend))
                throw new ArithmeticException("minuend is infinite.");
            if (Double.isNaN(minuend))
                throw new ArithmeticException("minuend is Nan.");
            if (Double.isInfinite(subtrahend))
                throw new ArithmeticException("subtrahend is infinite.");
            if (Double.isNaN(subtrahend))
                throw new ArithmeticException("subtrahend is Nan.");
            throw e;
        }
    }

    /**
     * 提供精確的乘法運算。
     * 
     * @param mulitplicand
     *            被乘數
     * @param multiplier
     *            乘數
     * @return 兩個參數的積
     */
    public static double mul(double mulitplicand, double multiplier) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(mulitplicand));
            BigDecimal b2 = new BigDecimal(Double.toString(multiplier));
            return b1.multiply(b2).doubleValue();
        } catch (Exception e) {
            if (Double.isInfinite(mulitplicand))
                throw new ArithmeticException("mulitplicand is infinite.");
            if (Double.isNaN(mulitplicand))
                throw new ArithmeticException("mulitplicand is Nan.");
            if (Double.isInfinite(multiplier))
                throw new ArithmeticException("multiplier is infinite.");
            if (Double.isNaN(multiplier))
                throw new ArithmeticException("multiplier is Nan.");
            throw e;
        }
    }

    /**
     * 提供（相對）精確的除法運算，當發生除不盡的情況時，精確到 小數點以後10位元，以後的數字四捨五入。
     * 
     * @param dividend
     *            被除數
     * @param divisor
     *            除數
     * @return 兩個參數的商
     */
    public static double div(double dividend, double divisor) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(dividend));
            BigDecimal b2 = new BigDecimal(Double.toString(divisor));
            return b1.divide(b2).doubleValue();
        } catch (Exception e) {
            if (divisor == 0)
                throw new ArithmeticException("divisor is zero.");
            if (Double.isInfinite(dividend))
                throw new ArithmeticException("dividend is infinite.");
            if (Double.isNaN(dividend))
                throw new ArithmeticException("dividend is Nan.");
            if (Double.isInfinite(divisor))
                throw new ArithmeticException("divisor is infinite.");
            if (Double.isNaN(divisor))
                throw new ArithmeticException("divisor is Nan.");
            throw e;
        }
    }

    /**
     * 提供（相對）精確的除法運算。當發生除不盡的情況時，由scale參數指 定精度，以後的數字四捨五入。
     * 
     * @param dividend
     *            被除數
     * @param divisor
     *            除數
     * @param scale
     *            表示表示需要精確到小數點以後幾位。
     * @return 兩個參數的商
     */
    public static double div(double dividend, double divisor, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(dividend));
            BigDecimal b2 = new BigDecimal(Double.toString(divisor));
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            if (divisor == 0)
                throw new ArithmeticException("divisor is zero.");
            if (Double.isInfinite(dividend))
                throw new ArithmeticException("dividend is infinite.");
            if (Double.isNaN(dividend))
                throw new ArithmeticException("dividend is Nan.");
            if (Double.isInfinite(divisor))
                throw new ArithmeticException("divisor is infinite.");
            if (Double.isNaN(divisor))
                throw new ArithmeticException("divisor is Nan.");
            throw e;
        }
    }

    /**
     * 提供精確的小數位四捨五入處理。
     * 
     * @param val
     *            需要四捨五入的數位
     * @param scale
     *            小數點後保留幾位
     * @return 四捨五入後的結果
     */
    public static double round(double val, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        try {
            BigDecimal b = new BigDecimal(Double.toString(val));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            if (Double.isInfinite(val))
                throw new ArithmeticException("round value is infinite.");
            if (Double.isNaN(val))
                throw new ArithmeticException("round value is Nan.");
            throw e;
        }
    }
    
    /**
     * 提供精確的小數位無條件進位處理。
     * 
     * @param val
     *            需要無條件進位的數位
     * @param scale
     *            小數點後保留幾位
     * @return 無條件進位後的結果
     */
    public static double up(double val, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        try {
            BigDecimal b = new BigDecimal(Double.toString(val));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_UP).doubleValue();
        } catch (Exception e) {
            if (Double.isInfinite(val))
                throw new ArithmeticException("up value is infinite.");
            if (Double.isNaN(val))
                throw new ArithmeticException("up value is Nan.");
            throw e;
        }
    }
    
    private static BigDecimal ONE = new BigDecimal("1");

    /**
     * 向最接近數字方向舍入的舍入模式，如果與兩個相鄰數字的距離相等，則向相鄰的偶數舍入。
     *<table border>
     *<tr valign=top><th>Input Number</th>
     *<th>Input rounded to one digit<br> with {@code HALF_EVEN} rounding
     *<tr align=right><td>5.5</td>  <td>6</td>
     *<tr align=right><td>2.5</td>  <td>2</td>
     *<tr align=right><td>1.6</td>  <td>2</td>
     *<tr align=right><td>1.1</td>  <td>1</td>
     *<tr align=right><td>1.0</td>  <td>1</td>
     *<tr align=right><td>-1.0</td> <td>-1</td>
     *<tr align=right><td>-1.1</td> <td>-1</td>
     *<tr align=right><td>-1.6</td> <td>-2</td>
     *<tr align=right><td>-2.5</td> <td>-2</td>
     *<tr align=right><td>-5.5</td> <td>-6</td>
     *</table>
     */
    public Arithmetic halfEven(int scale) {
        return scale(scale, RoundingMode.HALF_EVEN);
    }

    /**
     * 向最接近數字方向舍入的舍入模式，如果與兩個相鄰數字的距離相等，則向上舍入。
     *<table border>
     *<tr valign=top><th>Input Number</th>
     <th>Input rounded to one digit<br> with {@code HALF_UP} rounding
     *<tr align=right><td>5.5</td>  <td>6</td>
     *<tr align=right><td>2.5</td>  <td>3</td>
     *<tr align=right><td>1.6</td>  <td>2</td>
     *<tr align=right><td>1.1</td>  <td>1</td>
     *<tr align=right><td>1.0</td>  <td>1</td>
     *<tr align=right><td>-1.0</td> <td>-1</td>
     *<tr align=right><td>-1.1</td> <td>-1</td>
     *<tr align=right><td>-1.6</td> <td>-2</td>
     *<tr align=right><td>-2.5</td> <td>-3</td>
     *<tr align=right><td>-5.5</td> <td>-6</td>
     *</table>
     */
    public Arithmetic halfUp(int scale) {
        return scale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 向最接近數字方向舍入的舍入模式，如果與兩個相鄰數字的距離相等，則向下舍入。
     *<table border>
     *<tr valign=top><th>Input Number</th>
     *<th>Input rounded to one digit<br> with {@code HALF_DOWN} rounding
     *<tr align=right><td>5.5</td>  <td>5</td>
     *<tr align=right><td>2.5</td>  <td>2</td>
     *<tr align=right><td>1.6</td>  <td>2</td>
     *<tr align=right><td>1.1</td>  <td>1</td>
     *<tr align=right><td>1.0</td>  <td>1</td>
     *<tr align=right><td>-1.0</td> <td>-1</td>
     *<tr align=right><td>-1.1</td> <td>-1</td>
     *<tr align=right><td>-1.6</td> <td>-2</td>
     *<tr align=right><td>-2.5</td> <td>-2</td>
     *<tr align=right><td>-5.5</td> <td>-5</td>
     *</table>
     */
    public Arithmetic halfDown(int scale) {
        return scale(scale, RoundingMode.HALF_DOWN);
    }

    /**
     * 向正無限大方向舍入的舍入模式。
     *<table border>
     *<tr valign=top><th>Input Number</th>
     *<th>Input rounded to one digit<br> with {@code CEILING} rounding
     *<tr align=right><td>5.5</td>  <td>6</td>
     *<tr align=right><td>2.5</td>  <td>3</td>
     *<tr align=right><td>1.6</td>  <td>2</td>
     *<tr align=right><td>1.1</td>  <td>2</td>
     *<tr align=right><td>1.0</td>  <td>1</td>
     *<tr align=right><td>-1.0</td> <td>-1</td>
     *<tr align=right><td>-1.1</td> <td>-1</td>
     *<tr align=right><td>-1.6</td> <td>-1</td>
     *<tr align=right><td>-2.5</td> <td>-2</td>
     *<tr align=right><td>-5.5</td> <td>-5</td>
     *</table>
    */
    public Arithmetic ceil(int scale) {
        return scale(scale, RoundingMode.CEILING);
    }

    /**
     * 向負無限大方向舍入的舍入模式。
     *<table border>
     *<tr valign=top><th>Input Number</th>
     *<th>Input rounded to one digit<br> with {@code FLOOR} rounding
     *<tr align=right><td>5.5</td>  <td>5</td>
     *<tr align=right><td>2.5</td>  <td>2</td>
     *<tr align=right><td>1.6</td>  <td>1</td>
     *<tr align=right><td>1.1</td>  <td>1</td>
     *<tr align=right><td>1.0</td>  <td>1</td>
     *<tr align=right><td>-1.0</td> <td>-1</td>
     *<tr align=right><td>-1.1</td> <td>-2</td>
     *<tr align=right><td>-1.6</td> <td>-2</td>
     *<tr align=right><td>-2.5</td> <td>-3</td>
     *<tr align=right><td>-5.5</td> <td>-6</td>
     *</table>
     */
    public Arithmetic floor(int scale) {
        return scale(scale, RoundingMode.FLOOR);
    }

    /**
     * 向零方向舍入的舍入模式。
     *<table border>
     *<tr valign=top><th>Input Number</th>
     *<th>Input rounded to one digit<br> with {@code DOWN} rounding
     *<tr align=right><td>5.5</td>  <td>5</td>
     *<tr align=right><td>2.5</td>  <td>2</td>
     *<tr align=right><td>1.6</td>  <td>1</td>
     *<tr align=right><td>1.1</td>  <td>1</td>
     *<tr align=right><td>1.0</td>  <td>1</td>
     *<tr align=right><td>-1.0</td> <td>-1</td>
     *<tr align=right><td>-1.1</td> <td>-1</td>
     *<tr align=right><td>-1.6</td> <td>-1</td>
     *<tr align=right><td>-2.5</td> <td>-2</td>
     *<tr align=right><td>-5.5</td> <td>-5</td>
     *</table>
     */
    public Arithmetic down(int scale) {
        return scale(scale, RoundingMode.DOWN);
    }

    /**
     * 遠離零方向舍入的舍入模式。
     *<table border>
     *<tr valign=top><th>Input Number</th>
     *<th>Input rounded to one digit<br> with {@code UP} rounding
     *<tr align=right><td>5.5</td>  <td>6</td>
     *<tr align=right><td>2.5</td>  <td>3</td>
     *<tr align=right><td>1.6</td>  <td>2</td>
     *<tr align=right><td>1.1</td>  <td>2</td>
     *<tr align=right><td>1.0</td>  <td>1</td>
     *<tr align=right><td>-1.0</td> <td>-1</td>
     *<tr align=right><td>-1.1</td> <td>-2</td>
     *<tr align=right><td>-1.6</td> <td>-2</td>
     *<tr align=right><td>-2.5</td> <td>-3</td>
     *<tr align=right><td>-5.5</td> <td>-6</td>
     *</table>
    */
    public Arithmetic up(int scale) {
        return scale(scale, RoundingMode.UP);
    }

    private Arithmetic scale(int scale, RoundingMode roundingMode) {
        this.value = this.value.divide(ONE, scale, roundingMode);
        return this;
    }


}
