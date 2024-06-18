package com.adobe.responses;

import java.util.List;

/**
 * Represents a response containing a list of Roman numeral conversions within a specified range.
 */
public class RangeRomanNumeralResponse {
  private List<RomanNumeralResponse> conversions;

  /**
   * Constructs a RangeRomanNumeralResponse with the given list of Roman numeral conversions.
   *
   * @param conversions The list of Roman numeral responses.
   */
  public RangeRomanNumeralResponse(List<RomanNumeralResponse> conversions) {
    this.conversions = conversions;
  }

  public List<RomanNumeralResponse> getConversions() {
    return conversions;
  }

  public void setConversions(List<RomanNumeralResponse> conversions) {
    this.conversions = conversions;
  }
}
