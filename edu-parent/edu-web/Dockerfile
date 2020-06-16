FROM java:8
VOLUME /tmp/edu-web/
ADD target/edu-web-0.0.1.jar app.jar
ENTRYPOINT ["nohup", "java","-jar","/edu-web-0.0.1-sources.jar"]
