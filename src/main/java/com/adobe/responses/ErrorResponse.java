package com.adobe.responses;

/** Represents an error response with a message and an associated error code. */
public record ErrorResponse(String message, ErrorCode errorCode) {}
