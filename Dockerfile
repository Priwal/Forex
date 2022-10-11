FROM adoptopenjdk/openjdk8:ubi
ENV APP_HOME=/usr/app/
COPY build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]