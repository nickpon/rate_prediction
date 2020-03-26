FROM openjdk:8
RUN mkdir weather
COPY target/weather-2.1.6.RELEASE.jar /weather
WORKDIR /weather
EXPOSE 8081
CMD ["java", "-jar", "weather-2.1.6.RELEASE.jar"]
