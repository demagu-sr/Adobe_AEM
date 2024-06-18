package com.adobe.responses;

/** Represents a response containing an input integer and its corresponding Roman numeral output. */
public class RomanNumeralResponse {
  private String input;
  private String output;

  /**
   * Constructs a RomanNumeralResponse with the given input and output.
   *
   * @param input The input integer.
   * @param output The corresponding Roman numeral.
   */
  public RomanNumeralResponse(int input, String output) {
    this.setInput(input);
    this.setOutput(output);
  }

  public String getInput() {
    return input;
  }

  public void setInput(int input) {
    this.input = Integer.toString(input);
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }
}
