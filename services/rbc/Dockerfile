FROM openjdk:8
RUN mkdir rbc
COPY target/rbc-2.1.6.RELEASE.jar /rbc
WORKDIR /rbc
CMD ["java", "-jar", "rbc-2.1.6.RELEASE.jar"]
