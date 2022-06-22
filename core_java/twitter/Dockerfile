FROM openjdk:8-alpine
COPY target/twitter_app*.jar /usr/local/app/twitter/lib/twitter_app.jar
ENTRYPOINT ["java","-jar","/usr/local/app/twitter/lib/twitter_app.jar"]
