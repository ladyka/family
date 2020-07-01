git pull ; rm -rf build; JAVA_HOME=/opt/jdk/jdk-11.0.2 ./gradlew assemble ; /opt/jdk/jdk-11.0.2/bin/java -Dspring.profiles.active=prod,credentials -jar build/libs/*.jar
