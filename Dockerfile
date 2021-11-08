FROM openjdk:17-alpine
RUN addgroup -S thirumal && adduser -S thirumal -G thirumal
USER thirumal:thirumal
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} spring-boot-admin-server-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","spring-boot-admin-server-0.0.1-SNAPSHOT.jar"]
#--
ENV APPROOT="/app"
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib ${APPROOT}/lib
COPY ${DEPENDENCY}/META-INF ${APPROOT}/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes ${APPROOT}
ENTRYPOINT ["java","-cp","app:app/lib/*","in.thirumal.SpringBootAdminServerApplication"]
