package com.coding404.simulator.component;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class RabbitMQInitializer {

    @Value("${position.queue}")
    private String queueName; //전달할 큐이름
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private String port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @PostConstruct //이 클래스가 빈으로 등록될때  실행됨
    public void init() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);

        while(true) {
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {

                //매개변수 설명
                //1. 큐이름
                //2. 서버 재시작시 큐를 영구적으로 저장할지 여부
                //3. 큐를 현재 연결(Channel)에서만 사용 가능하게 할지 여부
                //4. 큐가 사용되지 않으면 자동 삭제할지 여부
                //5. 추가 옵션 설정 (맵타입)
                channel.queueDeclare(queueName, true, false, false, null);
                log.info("✅ 큐가 정상적으로 생성되었습니다:" + queueName);
                return;

            } catch (Exception e) {
                try {
                    log.info("✅ 큐가 준비되지 않았습니다. 5초후 재시도 합니다");
                    Thread.sleep(5000); // 5초 대기 후 재시도
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
