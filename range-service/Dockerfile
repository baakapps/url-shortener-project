FROM maven:3.8.3-openjdk-17 as maven_build
COPY ../pom.xml .
RUN mvn -h clean package
WORKDIR /build
COPY ../range-service/pom.xml .
COPY ../range-service/src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Dmaven.test.skip

FROM eclipse-temurin:17-jre-alpine
COPY --from=maven_build /build/target/*.jar app.jar
EXPOSE 5200
ENTRYPOINT ["java","-jar","app.jar"]