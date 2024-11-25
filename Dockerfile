# Build stage
FROM gradle:jdk17 AS builder
WORKDIR /build

# Copy the application files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Build the application
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# Run stage
FROM openjdk:17-slim
WORKDIR /app

# Set environment variables for MySQL connection
ENV MYSQL_HOST=database-1.c1a48iya04p2.us-east-2.rds.amazonaws.com
ENV MYSQL_PORT=3306
ENV MYSQL_DB=hr
ENV MYSQL_USER=econrich
ENV MYSQL_PASSWORD=econrich123
ENV COMMONAPI_SERVICE_KEY=IbFMDjhvlAw%2BvwhazsE%2FI7lb1gm8vJTBHGKpLvW32pKdPQ2jDd5bFtC1lXp1CTQoKts7aBXsB5Y7e7BP0Sl9tg%3D%3D
ENV COMMONAPI_BASE_URL=https://apis.data.go.kr/3450000/naMovementOrgService_new/getNaMovementOrg_v2

# Copy the built artifact
COPY --from=builder /build/build/libs/*.jar app.jar

# Create a directory for logs
RUN mkdir /app/logs

# Create application.properties with environment variables
RUN echo "spring.application.name=econrich\n\
springdoc.swagger-ui.path=/swagger-ui.html\n\
spring.profiles.active=common,aws\n\
server.port=9092\n\
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver\n\
spring.jpa.hibernate.ddl-auto=none                   \n\
spring.jpa.show-sql=true\n\
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect\n\
spring.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DB}\n\
spring.datasource.username=${MYSQL_USER}\n\
spring.datasource.password=${MYSQL_PASSWORD}\n\
commonapi.service-key=${COMMONAPI_SERVICE_KEY}\n\
commonapi.base-url=${COMMONAPI_BASE_URL}" > /app/application.properties

# Expose the application port
EXPOSE 9092

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/application.properties"]