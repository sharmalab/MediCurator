# Project Overview

MediCurator is a medical image data manager. It allows user to fetch image data from heterogeneous data sources, access them in a uniform way, and build a local image database without near duplicate images.


## Setting the Constants
Set the TCIA_API_KEY, DATA_BASE_DIR in ./src/main/java/edu/emory/bmi/medicurator/Constants.java

Also set PROXY_HOST, PROXY_PORT if necessary.


## Building and Executing Using Apache Maven 3.3.x
Building
--------
     mvn compile

Testing
--------
    mvn test

It is expected to have the TCIA_API_KEY set in the Constants.java to build with tests.


## Dependencies
This project depends on the below major projects.

* Infinispan 8.2 stable
* dcm4che 3
* Apache HTTP Client


