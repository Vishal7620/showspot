# Use Tomcat base image
FROM tomcat:9.0-jdk17-temurin

# Clean default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR to Tomcat webapps as ROOT.war
COPY target/dummy-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
