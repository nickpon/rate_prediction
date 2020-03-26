FROM openjdk:8
RUN mkdir prediction
COPY target/prediction-2.1.6.RELEASE.jar /prediction
WORKDIR /prediction
EXPOSE 8082
CMD ["java", "-jar", "prediction-2.1.6.RELEASE.jar"]
