# FROM oracle/graalvm-ce:22.1.0-java11 as graalvm
# FROM oracle/graalvm-ce:20.2.0-java11 as graalvm
FROM ghcr.io/graalvm/graalvm-ce:java17-21 as graalvm
COPY . /home/app/mn-websocket
WORKDIR /home/app/mn-websocket

RUN gu install native-image
RUN native-image -cp build/libs/mn-websocket-*-all.jar
 
# We use a Docker multi-stage build here so that we only take the compiled native Spring Boot app from the first build container
FROM oraclelinux:7-slim
# FROM frolvlad/alpine-glibc
COPY --from=graalvm /home/app/mn-websocket/mn-websocket /app/mn-websocket
RUN chmod +x ./app/mn-websocket
ENTRYPOINT ["/app/mn-websocket"]