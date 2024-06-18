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
  
