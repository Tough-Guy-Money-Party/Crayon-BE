# openjdk 17 버전의 alpine 리눅스 환경을 구성
FROM openjdk:17-alpine

# Node.js 및 npm 설치
RUN apk add --no-cache nodejs npm

# 작업 디렉토리 생성
WORKDIR /app/vite-notion-to-site

# pnpm 설치
RUN npm install -g pnpm

# Gradle 빌드를 위한 기본 작업 디렉토리로 돌아가기
WORKDIR /

ARG JAR_FILE=build/libs/*.jar

# JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} docker-springboot.jar

# 운영 및 개발에서 사용되는 환경 설정을 분리
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/docker-springboot.jar"]
