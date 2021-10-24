FROM openjdk:17-alpine
RUN addgroup -S thirumal && adduser -S thirumal -G thirumal
USER thirumal:thirumal
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /spring-boot-admin-server-0.0.1-SNAPSHOT/lib
COPY ${DEPENDENCY}/META-INF /spring-boot-admin-server-0.0.1-SNAPSHOT/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /spring-boot-admin-server-0.0.1-SNAPSHOT
ENTRYPOINT ["java","-cp","spring-boot-admin-server-0.0.1-SNAPSHOT:spring-boot-admin-server-0.0.1-SNAPSHOT/lib/*","in.thirumal.SpringBootAdminServerApplication"]
