package po.kinomorrigan.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils {
    public static double roundToTwoDecimalPlaces(double number) {
        BigDecimal bd = BigDecimal.valueOf(number);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
