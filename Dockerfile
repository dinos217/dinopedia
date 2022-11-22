FROM openjdk:17-ea-jdk-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} dinopedia.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "dinopedia.jar"]
# run clean package by default
CMD mvn -DskipTests=true clean package
