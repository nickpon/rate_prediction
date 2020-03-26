FROM openjdk:8
RUN mkdir hello
COPY target/hello-2.1.6.RELEASE.jar /hello
RUN ls
WORKDIR /hello
EXPOSE 8084
CMD ["java", "-jar", "hello-2.1.6.RELEASE.jar"]
