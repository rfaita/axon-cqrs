FROM openjdk:8-jdk-alpine

VOLUME /tmp
RUN apk add --no-cache curl
RUN apk add --no-cache netcat-openbsd
RUN apk add --no-cache bash

ARG JAR_FILE
ARG SCRIPT_DIR

ENV JAVA_OPTS "-Xmx256m -Xms128m"

ADD ${JAR_FILE} app.jar

ADD ${SCRIPT_DIR}/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ADD ${SCRIPT_DIR}/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
