package pl.auroramc.commons.format.decimal;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static pl.auroramc.commons.format.decimal.DecimalUtils.getIntegralPart;
import static pl.auroramc.commons.format.decimal.DecimalUtils.getLengthOfIntegralPart;

import java.math.BigDecimal;

public class DecimalFormatter {

  private static final String TRUNCATED_AMOUNT_DELIMITER = ".";
  private static final int INTEGRAL_PART_INIT_OFFSET = 1;
  private static final char[] SCALE_SUFFIXES = {'k', 'm', 'g', 't', 'p', 'e'};
  private static final long[] SCALE_FACTORS = {
    1_000,
    1_000_000,
    1_000_000_000,
    1_000_000_000_000L,
    1_000_000_000_000_000L,
    1_000_000_000_000_000_000L
  };
  private static final long SMALLEST_SCALE_FACTOR = 1_000;

  private DecimalFormatter() {}

  public static String getFormattedDecimal(final BigDecimal amount) {
    return getFormattedDecimal(amount.doubleValue());
  }

  public static String getFormattedDecimal(final double amount) {
    if (amount < SMALLEST_SCALE_FACTOR) {
      return getTruncatedAmount(amount);
    }

    final long integralPart = getIntegralPart(amount);
    final int integralPartLength =
        getLengthOfIntegralPart(integralPart) - INTEGRAL_PART_INIT_OFFSET;
    final int nearestScaleDivider = integralPartLength / 3 - 1;

    return getFormattedAmountWithSuffix(
        amount, SCALE_FACTORS[nearestScaleDivider], SCALE_SUFFIXES[nearestScaleDivider]);
  }

  private static String getFormattedAmountWithSuffix(
      final double amount, final double divisor, final char suffix) {
    return getTruncatedAmount(amount / divisor) + suffix;
  }

  private static String getTruncatedAmount(final double amount) {
    double fractionalPart = DecimalUtils.getFractionalPart(amount);
    if (fractionalPart < 0.01) {
      return Long.toString((long) amount);
    }

    fractionalPart *= 100;
    fractionalPart =
        (fractionalPart < 99 && fractionalPart % 1 >= 0.5)
            ? ceil(fractionalPart)
            : floor(fractionalPart);
    return (long) amount + TRUNCATED_AMOUNT_DELIMITER + (long) fractionalPart;
  }
}
