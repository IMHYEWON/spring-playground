# Observability

## OpenTelemetry Instrumentation with Java Agent

### 1. Opentelemetry Java Agent 다운받아서 실행하기
- 오픈텔레메트리 자바 에이전트 다운로드
    ```bash
    curl -L -O https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
    ```
  
- 오픈텔레메트리 자바 에이전트와 함께 애플리케이션 실행
    ```bash
    ./gradlew build 
  
    java -javaagent:opentelemetry-javaagent.jar \
         -jar build/libs/spring-playground-0.0.1-SNAPSHOT.jar
    ```
  ```bash
    hazel@janghyewon-ui-MacBookAir spring-playground % java -javaagent:opentelemetry-javaagent.jar -jar build/libs/spring-playground-0.0.1-SNAPSHOT.jar
    OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
    [otel.javaagent 2025-01-05 18:42:23:360 +0900] [main] INFO io.opentelemetry.javaagent.tooling.VersionLogger - opentelemetry-javaagent - version: 2.11.0
    18:42:25,168 |-INFO in ch.qos.logback.core.joran.spi.ConfigurationWatchList@6a0c7af6 - URL [jar:nested:/Users/hazel/Documents/workspace/spring-playground/build/libs/spring-playground-0.0.1-SNAPSHOT.jar/!BOOT-INF/classes/!/logback-spring.xml] is not of type file
    18:42:25,169 |-INFO in ch.qos.logback.core.joran.spi.ConfigurationWatchList@6a0c7af6 - URL [jar:nested:/Users/hazel/Documents/workspace/spring-playground/build/libs/spring-playground-0.0.1-SNAPSHOT.jar/!BOOT-INF/classes/!/logback-spring.xml] is not of type file
  ```
  - 혹은 아래처럼 인텔리제이에서 VM options & Program arguments 설정
    - Add VM options: `-javaagent:opentelemetry-javaagent.jar`
    - Add Program arguments: `-jar build/libs/spring-playground-0.0.1-SNAPSHOT.jar`
    ![img.png](img.png)

- 오픈텔레메트리 콘솔 옵션 설정해서 실행 (아직은 collector 없으므로, 콘솔에서 로깅)
    ```bash
    java -javaagent:opentelemetry-javaagent.jar \
         -Dotel.traces.exporter=logging \
         -Dotel.metrics.exporter=logging \
         -Dotel.logs.exporter=logging \
          -jar build/libs/spring-playground-0.0.1-SNAPSHOT.jar
    ```
  
- 로그 확인
  ```
  [otel.javaagent 2025-01-05 19:07:28:440 +0900] [http-nio-8080-exec-3] INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'POST' : 0b8668f4896a5aafccf3ce1cf860f223 f718bd702d18f121 CLIENT [tracer: io.opentelemetry.http-url-connection:2.11.0-alpha] AttributesMap{data={server.port=8081, http.response.status_code=200, url.full=http://127.0.0.1:8081/subjects/test-avro-topic-value/versions, thread.id=215, thread.name=http-nio-8080-exec-3, server.address=127.0.0.1, network.protocol.version=1.1, http.request.method=POST}, capacity=128, totalAddedValues=9}
  ```
  - `otel.javaagent`: 로그가 OpenTelemetry Java Agent에서 생성되었음을 나타냄.
  - `http-nio-8080-exec-3`: 로그를 생성한 스레드 이름 (HTTP 요청을 처리하는 스레드).
  - `io.opentelemetry.exporter.logging.LoggingSpanExporter`: 로그를 출력한 OpenTelemetry의 Span Exporter.
  - `POST`: HTTP 요청 메서드.
  - `0b8668f4896a5aafccf3ce1cf860f223`: 현재 Span의 ID.
    - `f718bd702d18f121`: Parent Span의 ID (상위 Span ID).
    - `CLIENT`: Span의 유형 (CLIENT는 클라이언트 요청에서 생성된 Span).
  - `[tracer: io.opentelemetry.http-url-connection:2.11.0-alpha]`: 트레이서를 생성한 OpenTelemetry 구성 요소와 버전.
  - `AttributesMap{data={...}}`: Span에 연결된 속성 정보.
    - `server.port=8081` : 요청이 전송된 서버의 포트 번호.
    - `http.response.status_code=200` : 서버로부터 반환된 HTTP 상태 코드 
    - `url.full=http://127.0.0.1:8081/subjects/test-avro-topic-value/versions` : 요청이 전송된 전체 URL. 
    - `thread.id=215` : 요청을 처리한 스레드의 ID. 
    - `thread.name=http-nio-8080-exec-3` : 요청을 처리한 스레드의 이름. 
    - `server.address=127.0.0.1` : 요청이 전송된 서버의 주소. 
    - `network.protocol.version=1.1` : 사용된 네트워크 프로토콜의 버전 (HTTP/1.1).


- (어플리케이션 도커 이미지로 실행하는 경우)
  ```dockerfile
  FROM ubuntu:20.04
  LABEL authors="hazel"
  
  ADD build/libs/playground.jar /playground.jar
  ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar
  
  ENTRYPOINT java -javaagent:/opentelemetry-javaagent.jar \
                  -Dotel.traces.exporter=logging \
                  -Dotel.metrics.exporter=logging \
                  -Dotel.logs.exporter=logging \
                  -jar /playground.jar
  
  ```
  ```dockerfile
  version: '3'
  services:
    my-app:
    build: ./ # Dockerfile 위치
    ports:
    - "8080:8080"
  ```

### 2. OpenTelemetry Java Agent 설정하기
#### 2.1. 어플리케이션 fatJar 이름 설정
```gradle
bootJar {
    archiveFileName = 'playground.jar'
}
```
#### 2.2. OpenTelemetry Java Agent build.gradle에 추가하기
manual하게 파일을 다운받는 것이 아닌, build.gradle에 의존성을 추가하여 자동으로 다운로드하도록 설정할 수 있음.
- custom configuration 추가
```gradle
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
    agent
}
```

- agent 의존성 추가, agent 파일을 복사하는 task 추가, bootJar task에 dependsOn 추가
```gradle
dependencies {
    agent "io.opentelemetry.javaagent:opentelemetry-javaagent:1.32.0"
...}


// opentelemetry-javaagent-1.32.0-all.jar -> opentelemetry-javaagent.jar
task copyAgent(type: Copy) {
    from configurations.agent {
        rename "opentelemetry-javaagent-.*\\.jar", "opentelemetry-javaagent.jar"
    }
    into layout.buildDirectory.dir("agent")
}

bootJar {
    dependsOn(copyAgent)
    archiveFileName = 'playground.jar'
}
```

어플리케이션 실행시 vm옵션 수정
```bash
java -javaagent:build/agent/opentelemetry-javaagent.jar \
      -Dotel.traces.exporter=logging \
      -Dotel.metrics.exporter=logging \
      -Dotel.logs.exporter=logging \
      -jar build/libs/playground.jar
```

(dockerfile로 어플리케이션 실행시)
```dockerfile
ADD build/libs/playground.jar /playground.jar
ADD build/agent/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

ENTRYPOINT java -javaagent:/opentelemetry-javaagent.jar \
                -Dotel.traces.exporter=logging \
                -Dotel.metrics.exporter=logging \
                -Dotel.logs.exporter=logging \
                -jar /playground.jar
```

### 3. OpenTelemetry Agent로 수집한 데이터 Jaeger로 전송하기
#### 3.1 Jaeger 설치
```dockerfile
  jaeger:
    image: jaegertracing/all-in-one:latest
    environment:
      - COLLECTOR_OTLP_ENABLED=true
      - COLLECTOR_GRPC_ENABLED=true # gRPC 포트 활성화
    restart: always
    ports:
      - "16686:16686" # for the Web UI
      - "4317:4317" # for gRPC protocol to send spans
```

- vm 옵션 수정
```bash
-javaagent:build/agent/opentelemetry-javaagent.jar
-Dotel.traces.exporter=otlp
-Dotel.exporter.otlp.endpoint=http://localhost:4317
-Dotel.metrics.exporter=logging
-Dotel.logs.exporter=none
```
(또는 dockerfile로 어플리케이션 실행시)
```dockerfile
ENTRYPOINT java -javaagent:/opentelemetry-javaagent.jar \
                -Dotel.traces.exporter=otlp \
                -Dotel.exporter.otlp.endpoint=http://localhost:4317 \
                -Dotel.metrics.exporter=logging \
                -Dotel.logs.exporter=logging \
                -jar /playground.jar
```


#### 3.2 jaeger UI 확인
- http://localhost:16686
- Trace 흐름
  1. Rest Controller에서 HTTP 요청을 받음 
  2. Kafka Producer로 메시지 전송
  3. Kafka Consumer로 메시지 수신
  4. Kafka Consumer에서 Stock 저장
![img_1.png](img_1.png)