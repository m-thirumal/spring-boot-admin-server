# spring-boot-admin-server

Spring boot admin server with Eureka

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

	![Spring boot Admin server and client with eureka](Spring boot Admin server and client with eureka.png)
