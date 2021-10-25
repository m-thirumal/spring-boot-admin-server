# spring-boot-admin-server

Run with [Eureka server/registry](https://github.com/M-Thirumal/eureka-server) 

* Spring boot admin will be the client of eureka-server

* Eureka-server will be the client of spring boot admin server

#### Login Details

URL     : [http://localhost:7216](http://localhost:7216)

Username: thirumal

Password: thirumal


##### The eureka client must expose the actuator end point


	management.endpoints.web.exposure.include=*
	management.endpoint.shutdown.enabled=true
	management.endpoints.beans.sensitive=false


##### Sample Eureka registration from client side


	eureka:
	  instance:
	    appname: indsolv-cloud-config-server
	    leaseRenewalIntervalInSeconds: 10
	    preferIpAddress: true
	    health-check-url-path: /actuator/health
	    metadata-map:
	      startup: ${random.int}    #needed to trigger info and endpoint update after restart
	      user:
	        name: "thirumal"
	        password: "thirumal"
	  client:
	    region: default
	    enabled: true
	    register-with-eureka: true
	    registryFetchIntervalSeconds: 5
	    fetch-registry: true
	    service-url:
	      defaultZone: http://thirumal:thirumal@localhost:8761/eureka/


## Architecture

![Spring-boot-Admin-server-client-with-eureka](output/Spring-boot-Admin-server-client-with-eureka.svg)

## Run as JAR

	./spring-boot-admin-server.sh
	
## Run in docker

* Create docker layered image using the following command
	
      mvn package
      mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
      docker build -t spring-boot-admin-server .
	
* Run the image
	
      docker run --restart always -d -p 7126:7126 spring-boot-admin-server

## Output

![1.png](output/1.png)

![2.png](output/2.png)

![3.png](output/3.png)

![4.png](output/4.png)
