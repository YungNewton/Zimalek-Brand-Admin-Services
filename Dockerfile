FROM openjdk:11-jre-slim-buster


RUN mkdir /uploads
# install java

EXPOSE 3002

ADD target/brand-admin-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar