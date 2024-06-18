package com.adobe.roman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.adobe.Constants;
import com.adobe.responses.RomanNumeralResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

class RomanNumeralConverterTest {
  private final RomanNumeralConverter converter = new RomanNumeralConverter();

  @Test
  void testConvertBasic() throws InvalidInputException {
    assertEquals("VIII", converter.convert(8));
    assertEquals("XXXIX", converter.convert(39));
    assertEquals("DCCLXXXIX", converter.convert(789));
    assertEquals("MIX", converter.convert(1009));
    assertEquals("MMXXIV", converter.convert(2024));
  }

  @Test
  void testConvertInclusiveRange() throws InvalidInputException {
    assertEquals("I", converter.convert(Constants.MINIMUM_INTEGER_SUPPORTED));
    assertEquals("MMMCMXCIX", converter.convert(Constants.MAXIMUM_INTEGER_SUPPORTED));
  }

  @Test
  void testConvertInvalidInput() {
    // test for inputs outside the range
    assertThrows(
        InvalidInputException.class,
        () -> converter.convert(Constants.MINIMUM_INTEGER_SUPPORTED - 1));
    assertThrows(
        InvalidInputException.class,
        () -> converter.convert(Constants.MAXIMUM_INTEGER_SUPPORTED + 1));
  }

  @Test
  void testRangeConvertBasic() throws InvalidInputException {
    List<RomanNumeralResponse> responses = converter.rangeConvert(1, 10);

    assertEquals(10, responses.size());

    assertRomanNumeralResponse(1, "I", responses.get(0));
    assertRomanNumeralResponse(2, "II", responses.get(1));
    assertRomanNumeralResponse(3, "III", responses.get(2));
    assertRomanNumeralResponse(4, "IV", responses.get(3));
    assertRomanNumeralResponse(5, "V", responses.get(4));
    assertRomanNumeralResponse(6, "VI", responses.get(5));
    assertRomanNumeralResponse(7, "VII", responses.get(6));
    assertRomanNumeralResponse(8, "VIII", responses.get(7));
    assertRomanNumeralResponse(9, "IX", responses.get(8));
    assertRomanNumeralResponse(10, "X", responses.get(9));
  }

  @Test
  void testRangeConvertInvalidInput() {
    // test for inputs outside the range 1-3999
    assertThrows(InvalidInputException.class, () -> converter.rangeConvert(-1, 10));
    assertThrows(InvalidInputException.class, () -> converter.rangeConvert(1, 10000));

    // test for input where min > max
    assertThrows(InvalidInputException.class, () -> converter.rangeConvert(20, 10));
  }

  private void assertRomanNumeralResponse(int input, String output, RomanNumeralResponse response) {
    assertEquals(Integer.toString(input), response.getInput());
    assertEquals(output, response.getOutput());
  }
}
