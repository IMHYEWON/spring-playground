FROM ubuntu:20.04
LABEL authors="hazel"

RUN apt-get update && apt-get install -y openjdk-17-jdk

ADD build/libs/playground.jar /playground.jar
ADD build/agent/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

ENTRYPOINT java -javaagent:/opentelemetry-javaagent.jar \
                -Dotel.traces.exporter=otlp \
                -Dotel.exporter.otlp.endpoint=http://localhost:4317 \
                -Dotel.metrics.exporter=logging \
                -Dotel.logs.exporter=logging \
                -jar /playground.jar

