package com.adobe.roman;

import com.adobe.responses.RomanNumeralResponse;
import java.util.List;

/** Interface that provide APIs for converting integers to Roman numerals. */
public interface IRomanNumeralConverter {

  /**
   * Converts an integer to its corresponding Roman numeral representation.
   *
   * @param input The integer value to convert.
   * @return The Roman numeral as a string.
   * @throws InvalidInputException If the input value is outside the valid range.
   */
  String convert(int input) throws InvalidInputException;

  /**
   * Converts a range of integers to their corresponding Roman numeral representations.
   *
   * @param min The minimum value in the range (inclusive).
   * @param max The maximum value in the range (inclusive).
   * @return A list of RomanNumeralResponse objects, each containing the input value and its Roman
   *     numeral.
   * @throws InvalidInputException If the input range is invalid.
   */
  List<RomanNumeralResponse> rangeConvert(int min, int max) throws InvalidInputException;
}
