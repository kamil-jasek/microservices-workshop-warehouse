FROM gcr.io/distroless/java17-debian11
COPY /target/app.jar /app.jar
CMD ["app.jar"]
