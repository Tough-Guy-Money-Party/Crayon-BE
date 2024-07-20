# open jdk 17 버전의 alpine 리눅스 환경을 구성
FROM openjdk:17-alpine

# build가 되는 시점에 JAR_FILE이라는 변수 명에 build/libs/*.jar 선언
# build/libs - gradle로 빌드했을 때 jar 파일이 생성되는 경로
ARG JAR_FILE=build/libs/*.jar

# JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} docker-springboot.jar

# 환경 변수 파일 복사
COPY crayon.env .

# 환경 변수 로드
ENV USERNAME=${USERNAME}
ENV PASSWORD=${PASSWORD}
ENV REDIS_HOST=${REDIS_HOST}
ENV REDIS_PORT=${REDIS_PORT}
ENV JWT_ACCESS_SECRET=${JWT_ACCESS_SECRET}
ENV JWT_REFRESH_SECRET=${JWT_REFRESH_SECRET}
ENV LAMBDA_URI=${LAMBDA_URI}
ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
ENV KAKAO_TOKEN_URI=${KAKAO_TOKEN_URI}
ENV KAKAO_REDIRECT_URI=${KAKAO_REDIRECT_URI}
ENV KAKAO_GRANT_TYPE=${KAKAO_GRANT_TYPE}
ENV KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID}
ENV KAKAO_USER_INFO_URI=${KAKAO_USER_INFO_URI}

# 운영 및 개발에서 사용되는 환경 설정을 분리
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/docker-springboot.jar"]