FROM openjdk:17-alpine
RUN addgroup -S thirumal && adduser -S thirumal -G thirumal
USER thirumal:thirumal
ENV APPROOT="/app"
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib ${APPROOT}/lib
COPY ${DEPENDENCY}/META-INF ${APPROOT}/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes ${APPROOT}
ENTRYPOINT ["java","-cp","app:app/lib/*","in.thirumal.SpringBootAdminServerApplication"]
