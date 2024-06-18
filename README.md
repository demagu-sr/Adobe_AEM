# Overview of the Spring Boot Web Service
This is a Spring Boot web service provides that provides Roman numeral conversion functionality. It exposes two endpoints:

### Integer to Roman Numeral Conversion (v1)
<em>Endpoint</em>:
> /romannumeral/v1

<em>Description:</em> Converts an integer input to its corresponding Roman numeral representation.

<em>Supported Range:</em> 1 to 3999 (inclusive)

<em>Example</em>
> 
> Request: GET /romannumeral/v1?query=42
> 
> Response: {"input": "42", "output": "XLII"}

### Range Roman Numeral Conversion
<em>Endpoint:</em>
> /romannumeral

<em>Description:</em> Converts a range of integers to a list of Roman numeral responses.

<em>Supported Range:</em> 1 to 3999 (inclusive).

<em>Example</em>
> 
> Request: GET /romannumeral?min=10&max=15
>
> Response: [{"input": "10", "output": "X"}, {"input": "11", "output": "XI"}, ...]

### Health Check Endpoint
<em>Example</em>:
>
> /healthcheck

<em>Description:</em> Returns an HTTP 200 (OK) response with the body “Success.” This endpoint can be used for monitoring the service health.

<em>Example</em>

> Request: GET /healthcheck

> Response: HTTP/1.1 200 OK Content-Type: text/plain Success

## Roman Numeral Specification:

* [Wiki](https://en.wikipedia.org/wiki/Roman_numerals)


## Build 

This project is built, packaged through maven including dependency management. Tests are included to run as part of the build. This web service is built as a docker image to make it easier for deploying on containerized solutions. Docker file is configured with all the dependencies including building, packaging and installing all the required dependencies

### Instructions

* Build the docker image using below command
  * docker build -t adobe_aem
* Run the docker image to start the web server
  * docker run -p 8080:80 adobe_aem:latest

## Packaging Layout

The packaging layout is based on standard bootstrap java web service

## Engineering and Testing Methodology

Test driven development is one of the popular test methodologies followed across the industry and emphasizes on writing tests before the actual code. This helps catch various edge cases and makes the code reliable and robust. This project contains good unit test coverage for the code

## Dependency attribution

This project uses few standard open source libraries like junit, spring-boot-framework, maven, slf4j logger library, docker, open jdk17

## Extensions

In future, this project can be extended to

* Cache the results and not compute IntegerToRoman conversion everytime. This can be implemented either by computing all the range of supported values at service bootstrap and only served through cache or it can also be implemented as write-through cache, which would check the cache and update if not present.
* Every service requires monitoring/alerting story to ensure reliability and catch issues sooner. Currently, there is no monitoring for this service and below scenarios are good to start with
  * Use healthcheck endpoint to verify the heartbeat of the service
  * Monitor and get alerted on the number of endpoint failures
  * Monitor and get alerted on number of OOMs for the service
  
