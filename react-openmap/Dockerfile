# Step 1: Nginx의 최신 Alpine 기반 이미지를 사용
FROM nginx:alpine

# Step 2: Nginx 기본 설정 파일 삭제
RUN rm -rf /etc/nginx/conf.d/default.conf

# Step 3: React 빌드된 파일을 Nginx의 기본 웹 디렉토리로 복사
COPY build/ /usr/share/nginx/html

# Step 4: 커스텀 Nginx 설정 파일을 복사
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Step 5: Nginx를 실행
CMD ["nginx", "-g", "daemon off;"]