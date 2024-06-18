package com.adobe.service.controller;

import com.adobe.responses.ErrorCode;
import com.adobe.responses.ErrorResponse;
import com.adobe.responses.RangeRomanNumeralResponse;
import com.adobe.responses.RomanNumeralResponse;
import com.adobe.roman.InvalidInputException;
import com.adobe.roman.RomanNumeralConverter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** REST controller for Roman numeral conversions. */
@RestController
public class RomanNumeralController {

  private static final Logger logger = LoggerFactory.getLogger(RomanNumeralController.class);
  private final RomanNumeralConverter converter;

  /** Default constructor */
  public RomanNumeralController() {
    this(new RomanNumeralConverter());
  }

  /**
   * Constructor for dependency injection used for unit testing
   *
   * @param romanNumeralConverter RomanNumeralConverter instance
   */
  public RomanNumeralController(RomanNumeralConverter romanNumeralConverter) {
    this.converter = romanNumeralConverter;
  }

  /**
   * Health check endpoint. Returns an HTTP 200 (OK) response with the body "Success." This can be
   * used for monitoring the service health
   *
   * @return A ResponseEntity with the success message.
   */
  @GetMapping("/healthcheck")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.status(HttpStatus.OK).body("Success");
  }

  /**
   * Converts an integer input to a Roman numeral. This is the version v1 API that converts integer
   * roman numeral and supports range from 1-3999
   *
   * @param input The integer input.
   * @return A ResponseEntity with the Roman numeral response.
   * @throws InvalidInputException If the input is invalid.
   */
  @GetMapping("/romannumeral/v1")
  public ResponseEntity<RomanNumeralResponse> integerToRomanConversion(
      @RequestParam("query") int input) throws InvalidInputException {
    String romanNumeral = converter.convert(input);
    return ResponseEntity.status(HttpStatus.OK).body(new RomanNumeralResponse(input, romanNumeral));
  }

  /**
   * Converts a range of integers to Roman numerals.
   *
   * @param min The minimum value in the range.
   * @param max The maximum value in the range.
   * @return A ResponseEntity with the range Roman numeral response.
   * @throws InvalidInputException If the input is invalid.
   */
  @GetMapping("/romannumeral")
  public ResponseEntity<RangeRomanNumeralResponse> rangeRomanConversion(
      @RequestParam("min") int min, @RequestParam("max") int max) throws InvalidInputException {
    List<RomanNumeralResponse> result = converter.rangeConvert(min, max);

    return ResponseEntity.status(HttpStatus.OK).body(new RangeRomanNumeralResponse(result));
  }

  /**
   * Exception handler for handling InvalidInputException. Constructs an error response with the
   * exception message and error code.
   *
   * @param exception The InvalidInputException that occurred.
   * @return A ResponseEntity containing the error response.
   */
  @ExceptionHandler(InvalidInputException.class)
  public ResponseEntity<ErrorResponse> handleInvalidInputException(
      InvalidInputException exception) {
    logger.error("Invalid input exception occurred: {}", exception.getMessage());
    ErrorResponse errorResponse =
        new ErrorResponse(exception.getMessage(), ErrorCode.INVALID_INPUT);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Exception handler for handling unhandled exceptions. Constructs an error response with the
   * exception message and error code.
   *
   * @param exception The Exception that occurred.
   * @return A ResponseEntity containing the error response.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnhandledException(Exception exception) {
    logger.error("Unhandled exception occurred: {}", exception.getMessage());
    ErrorResponse errorResponse =
        new ErrorResponse(exception.getMessage(), ErrorCode.INTERNAL_ERROR);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
