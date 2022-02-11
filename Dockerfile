FROM openjdk:8-alpine

COPY target/uberjar/contactapp.jar /contactapp/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/contactapp/app.jar"]
