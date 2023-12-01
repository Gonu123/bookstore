FROM gcr.io/distroless/java17-debian12

COPY ./build/libs/bookstore-0.0.1-SNAPSHOT.jar ./service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "service.jar"]