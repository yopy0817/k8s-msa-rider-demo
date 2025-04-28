# RabbitMQ 공식 이미지 (management 플러그인 포함)
FROM rabbitmq:management

# 1st - 기본설정으로 사용자 계정만 만들경우
# ENV RABBITMQ_DEFAULT_USER=guest
# ENV RABBITMQ_DEFAULT_PASS=guest

# 2nd - 설정파일 만들어서 사용할 경우
COPY definitions.json /etc/rabbitmq/definitions.json
COPY rabbitmq.conf /etc/rabbitmq/rabbitmq.conf


# RabbitMQ 실행
CMD ["rabbitmq-server"]	