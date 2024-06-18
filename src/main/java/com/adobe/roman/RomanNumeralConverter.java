package com.adobe.roman;

import com.adobe.Constants;
import com.adobe.responses.RomanNumeralResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation class that converts integers to Roman numerals. Roman Numeral Specification: <a
 * href="https://en.wikipedia.org/wiki/Roman_numerals">...</a>
 */
public class RomanNumeralConverter implements IRomanNumeralConverter {

  private static final Logger logger = LoggerFactory.getLogger(RomanNumeralConverter.class);
  private static final int[] romanIntegerValues = {
    1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
  };
  private static final String[] romanSymbols = {
    "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
  };

  /**
   * Converts an integer to its corresponding Roman numeral representation.
   *
   * @param input The integer value to convert.
   * @return The Roman numeral as a string.
   * @throws InvalidInputException If the input value is outside the valid range.
   */
  @Override
  public String convert(int input) throws InvalidInputException {
    // validate input
    validateInput(input);
    return integerToRoman(input);
  }

  /**
   * Converts a range of integers to their corresponding Roman numeral representations.
   *
   * @param min The minimum value in the range (inclusive).
   * @param max The maximum value in the range (inclusive).
   * @return A list of RomanNumeralResponse objects, each containing the input value and its Roman
   *     numeral.
   * @throws InvalidInputException If the input range is invalid.
   */
  @Override
  public List<RomanNumeralResponse> rangeConvert(int min, int max) throws InvalidInputException {
    // verify that the input is in supported range
    validateRange(min, max);

    // process parallel conversions using streams
    // Reference: https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html
    return IntStream.rangeClosed(min, max)
        .parallel()
        .mapToObj(current -> new RomanNumeralResponse(current, this.integerToRoman(current)))
        // Ensure that comparison is done using Integer as sorting IntegerStrings will yield
        // incorrect sort order
        .sorted(Comparator.comparingInt(response -> Integer.parseInt(response.getInput())))
        .toList();
  }

  /**
   * This method does the actual conversion from integer to roman numeral
   *
   * @param input The integer value to convert
   * @return The roman numeral as String
   */
  private String integerToRoman(int input) {
    StringBuilder result = new StringBuilder();

    // Traverse symbols from high to low value. For every symbol value, reduce
    // its value from original input and append the symbol
    // till no longer possible and move onto the next symbol.
    for (int index = 0; index < romanIntegerValues.length; index++) {
      while (romanIntegerValues[index] <= input) {
        input = input - romanIntegerValues[index];
        result.append(romanSymbols[index]);
      }
    }

    return result.toString();
  }

  /**
   * Validates whether the input integer falls within the supported range.
   *
   * @param input The input integer to validate.
   * @throws InvalidInputException If the input is outside the valid range.
   */
  private void validateInput(int input) throws InvalidInputException {
    if (input < Constants.MINIMUM_INTEGER_SUPPORTED
        || input > Constants.MAXIMUM_INTEGER_SUPPORTED) {
      String errorMessage =
          String.format(
              "Input should be between %d and %d",
              Constants.MINIMUM_INTEGER_SUPPORTED, Constants.MAXIMUM_INTEGER_SUPPORTED);
      logger.error(errorMessage);
      throw new InvalidInputException(errorMessage);
    }
  }

  /**
   * Validates whether the given range (min to max) is valid.
   *
   * @param min The minimum value in the range.
   * @param max The maximum value in the range.
   * @throws InvalidInputException If the range is invalid.
   */
  private void validateRange(int min, int max) throws InvalidInputException {
    validateInput(min);
    validateInput(max);

    if (min > max) {
      String errorMessage =
          String.format(
              "min value %d should be less than max value %d and within the range (%d, %d) inclusive",
              min, max, Constants.MINIMUM_INTEGER_SUPPORTED, Constants.MAXIMUM_INTEGER_SUPPORTED);
      logger.error(errorMessage);
      throw new InvalidInputException(errorMessage);
    }
  }
}
