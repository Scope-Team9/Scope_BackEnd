FROM adoptopenjdk/openjdk11
ENV JAVA_OPTS=""
WORKDIR /scope
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java","-jar","-Djasypt.encryptor.password=${password}","-Dspring.profiles.active=${active}","Scope-0.0.1-SNAPSHOT.jar"]