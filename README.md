# VFIE-DXL-CSM-check-service-feasibility-ts
This CSM is the microservice responsible to access OSB's service CheckCustomerFeasibility.

## Prerequisites

In order to work with this project we need to previously install the following tools and plugins:

* Java 11 - https://www.oracle.com/java/technologies/downloads/#java11
* Maven - https://maven.apache.org/install.html
* MongoDB - https://www.mongodb.com/try/download/community
    * The database and collections specified in [config.properties](https://github.vodafone.com/VFIE-DigitalEngineering/VFIE-DXL-LIB-OSB-Layer/blob/development/src/main/resources/config.properties) should be set up by adding data with the following structure.
   ```json
  {
  "_id": {
    "$oid": "613089eff9f09742f5b90168"
  },
  "id": 2,
  "name": "dxl.service.checkcustomerfeasibility",
  "wsdl": "https://github.vodafone.com/VFIE-DigitalEngineering/DXL-IE-Configs/blob/master/wsdl//CheckServiceFeasibility/CheckServiceFeasibilityService.WSDL",
  "endpoint": "https://ieesbbvr.dc-dublin.de:9007/CheckServiceFeasibility/CheckServiceFeasibilityService",
  "username": "***",
  "password": "***",
  "timeout": 15000,
  "connectionTimeout": 30000}

### Compile and package the project

The project is compose by two modules:
- dto - Module that contain all the models needed to communicate with this microservice, both for requests and responses.
- service - Module that contain all the logic to call, map and cache requests to OSB.

First we need to compile the dto module, then the service module using maven
```bash
mvn clean install
```

After that, we need to run the service on quarkus
```bash
mvn compile quarkus:dev
```

## Avaliable API services

We have four main API that can be called

#### CheckCustomerFeasibility

Check Service Feasibility is used to return the list of feasibility options for requested services in a specific location

## Usage

1. All CSM are RESTful API that need to be called by TMF to access OSB information.

2. To call the services, first we need to import the dto maven dependency

```xml
<dependency>
    <groupId>ie.vodafone.dxl</groupId>
    <artifactId>check-service-feasibility-dto</artifactId>
    <version>${ie.vodafone.dxl.checkservicefeasibility.dto.version}</version>
</dependency>
 ```
3. We need to fulfill the information needed in the request of the API we want to execute

find confluence for more details 
https://confluence.sp.vodafone.com/display/DDEI/CSM+Check+Service+Feasibility
