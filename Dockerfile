FROM eclipse-temurin:17-jre-alpine
LABEL version="1.0"
LABEL maintainer="Ivan Shilov"
ADD target/test.jar ./test.jar
ENTRYPOINT ["java", "-jar", "./test.jar"]
