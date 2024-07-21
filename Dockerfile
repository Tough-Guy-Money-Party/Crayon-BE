# open jdk 17 버전의 alpine 리눅스 환경을 구성
FROM openjdk:17-alpine

ENV USERNAME=${USERNAME} \
    PASSWORD=$PASSWORD \
    REDIS_HOST=$REDIS_HOST \
    REDIS_PORT=$REDIS_PORT \
    JWT_ACCESS_SECRET=$JWT_ACCESS_SECRET \
    JWT_REFRESH_SECRET=$JWT_REFRESH_SECRET \
    LAMBDA_URI=$LAMBDA_URI \
    AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
    AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
    KAKAO_TOKEN_URI=$KAKAO_TOKEN_URI \
    KAKAO_REDIRECT_URI=$KAKAO_REDIRECT_URI \
    KAKAO_GRANT_TYPE=$KAKAO_GRANT_TYPE \
    KAKAO_CLIENT_ID=$KAKAO_CLIENT_ID \
    KAKAO_USER_INFO_URI=$KAKAO_USER_INFO_URI


# build가 되는 시점에 JAR_FILE이라는 변수 명에 build/libs/*.jar 선언
# build/libs - gradle로 빌드했을 때 jar 파일이 생성되는 경로
ARG JAR_FILE=build/libs/*.jar

# JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} docker-springboot.jar

# 운영 및 개발에서 사용되는 환경 설정을 분리
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/docker-springboot.jar"]