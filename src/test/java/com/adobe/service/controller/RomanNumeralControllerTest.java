package com.adobe.service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.adobe.responses.ErrorResponse;
import com.adobe.responses.RangeRomanNumeralResponse;
import com.adobe.responses.RomanNumeralResponse;
import com.adobe.roman.InvalidInputException;
import com.adobe.roman.RomanNumeralConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Supress warning for unchecked cast
@SuppressWarnings("ALL")
class RomanNumeralControllerTest {

  @Mock private RomanNumeralConverter mockConverter;

  @BeforeEach
  void setUp() {
    // initialize all the mocks
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testIntegerToRomanConversion_Basic() throws InvalidInputException {
    RomanNumeralResponse romanNumeralResponse = new RomanNumeralResponse(10, "X");
    when(mockConverter.convert(anyInt())).thenReturn(String.valueOf(romanNumeralResponse));
    RomanNumeralController controller = new RomanNumeralController(mockConverter);

    ResponseEntity<RomanNumeralResponse> response =
        (ResponseEntity<RomanNumeralResponse>) controller.integerToRomanConversion(10);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testIntegerToRomanConversion_InvalidInput() throws InvalidInputException {
    InvalidInputException exception = new InvalidInputException("Invalid input");
    when(mockConverter.convert(anyInt())).thenThrow(exception);
    RomanNumeralController controller = new RomanNumeralController(mockConverter);
    ResponseEntity<ErrorResponse> response = controller.handleInvalidInputException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void testRangeRomanConversion_Basic() throws InvalidInputException {
    List<RomanNumeralResponse> romanNumeralResponseList = new ArrayList<>();
    when(mockConverter.rangeConvert(anyInt(), anyInt())).thenReturn(romanNumeralResponseList);
    RomanNumeralController controller = new RomanNumeralController(mockConverter);

    ResponseEntity<RangeRomanNumeralResponse> response =
        (ResponseEntity<RangeRomanNumeralResponse>) controller.rangeRomanConversion(10, 20);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testRangeRomanConversion_InvalidInput() throws InvalidInputException {
    InvalidInputException exception = new InvalidInputException("Invalid input");
    when(mockConverter.rangeConvert(anyInt(), anyInt())).thenThrow(exception);
    RomanNumeralController controller = new RomanNumeralController(mockConverter);
    ResponseEntity<ErrorResponse> response = controller.handleInvalidInputException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void testRangeRomanConversion_InternalServerError() throws InvalidInputException {
    Exception exception = new IllegalArgumentException("Some unhandled exception");
    when(mockConverter.rangeConvert(anyInt(), anyInt())).thenThrow(exception);
    RomanNumeralController controller = new RomanNumeralController(mockConverter);
    ResponseEntity<ErrorResponse> response = controller.handleUnhandledException(exception);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }
}
