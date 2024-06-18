package com.adobe.roman;

/** Exception class for defining invalid input in Roman numeral conversion. */
public class InvalidInputException extends Exception {

  /**
   * Constructs an InvalidInputException with the specified error message.
   *
   * @param errorMessage The error message describing the reason for the exception.
   */
  public InvalidInputException(String errorMessage) {
    super(errorMessage);
  }
}
