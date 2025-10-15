FROM maven:3.9.11-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN apk add --no-cache curl

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
