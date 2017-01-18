FROM java:8u111-jre
VOLUME /config
VOLUME /logs
ADD platformd-1.jar platform.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar platform.jar" ]

#run -d --net host -v /home/xtrit/platformd/config:/config <image-id>