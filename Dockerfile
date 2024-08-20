# 베이스 이미지 설정 (예: OpenJDK 17을 포함한 Node.js)
FROM openjdk:17-jdk-slim

# Node.js 및 npm 설치 (예: Node.js 18 버전)
RUN apt-get update && apt-get install -y curl
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash -
RUN apt-get install -y nodejs

# pnpm 설치
RUN npm install -g pnpm

# 나머지 애플리케이션 설정 및 빌드
WORKDIR /app
COPY . /app

# Gradle 빌드 (또는 다른 빌드 도구 사용)
RUN ./gradlew build

# 컨테이너 실행 시 실행할 명령어
CMD ["java", "-jar", "your-app.jar"]
