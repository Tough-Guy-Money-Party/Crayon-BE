# openjdk 17 버전의 alpine 리눅스 환경을 구성
FROM openjdk:17-alpine

# Node.js 18 및 npm 설치
RUN apk add --no-cache curl bash xz \
    && apk add --no-cache --repository=http://dl-cdn.alpinelinux.org/alpine/edge/community/ --repository=http://dl-cdn.alpinelinux.org/alpine/edge/main/ nodejs-current npm

# pnpm 설치
RUN npm install -g pnpm

# 작업 디렉토리 생성 및 Vite 프로젝트 의존성 설치
WORKDIR /app/vite-notion-to-site

RUN npm install vite

# Gradle 빌드를 위한 기본 작업 디렉토리로 돌아가기
WORKDIR /

ARG JAR_FILE=build/libs/*.jar

# JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} docker-springboot.jar

# 운영 및 개발에서 사용되는 환경 설정을 분리
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/docker-springboot.jar"]
