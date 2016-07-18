# Project Overview

MediCurator is a medical image data manager. It allows user to fetch image data from heterogeneous data sources, access them in a uniform way, and build a local image database without near duplicate images.


## Setting the Constants
Set the TCIA_API_KEY, DATA_BASE_DIR in ./src/main/java/edu/emory/bmi/medicurator/Constants.java

Also set PROXY_HOST, PROXY_PORT if necessary.

The user can choose use HdfsStorage or LocalStorage by changing STORAGE (hdfs/local) in Constants.java.
To use HDFS, you should config HDFS_URI and HDFS_BASEDIR in Constants.java.
For example, HDFS_URI = "hdfs://localhost:9000/"  and  HDFS_BASEDIR = "/user/xxx/medicurator/"


## Building and Executing Using Apache Maven 3.3.x
Building
--------
     mvn compile

Testing
--------
    mvn test

Run webapp
--------
    mvn tomcat7:run -DskipTests

It is expected to have the TCIA_API_KEY set in the Constants.java to build with tests.

The webapp runs at http://localhost:2222/Index

You may want to run

    mvn exec:java -Dexec.mainClass="edu.emory.bmi.medicurator.infinispan.StartInfinispan" 

first so that data you hava stored beforehand won't be lost although the web server has been closed.


## Dependencies
This project depends on the below major projects.

* Infinispan 8.2 stable
* dcm4che 3
* Apache HTTP Client
* Tomcat 7

