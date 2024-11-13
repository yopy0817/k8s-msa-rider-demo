package com.coding404.simulator.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


@Log4j2
@AllArgsConstructor
public class Rider implements Callable<Object> {

    private String vehicleName; //라이더명
    private List<String> position; //위치값
    private String queueName; //큐이름
    private RabbitTemplate rabbitTemplate; //메시지큐
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Object call() throws Exception {
        log.info(Thread.currentThread() + "동작됨");

        int index = 0; //리스트 제어 변수
        boolean forward = true; //리스트 탐색 방향 플래그

        //리스트 왕복으로 동작함
        while(true) {

            Map<String, String> map = new HashMap<>();

            String line = position.get(index);
            String[] data = line.split("\"");
            String lat = data[1];
            String lng = data[3];

            map.put("vehicle", vehicleName);
            map.put("lat", lat);
            map.put("lng", lng);
            map.put("date", sdf.format(new Date()) );

            sendMessage(map); //메시지큐에 전송

            //순방향 else 역방향
            if(forward) {
                index++;
                if(index == position.size() - 1) forward = false;
            } else {
                index--;
                if(index == 0) forward = true;
            }
            //딜레이
            delay( Math.random() * 10000 + 10000 ); //10~20초 사이로 딜레이
        }
    }

    //RabbitMQ로 메시지 송신
    public void sendMessage(Map<String, String> messageMap) throws InterruptedException {

        boolean messageNotSend = true;
        while(messageNotSend) {
            try {
                rabbitTemplate.convertAndSend(queueName, messageMap );
                messageNotSend = false;
                log.info("queue send success");

            } catch (Exception e) {
                log.info("큐 전송 실패 5초후 재시도 합니다.");
                delay(5000); //전송 실패시 5초 후에 재시도 합니다.
                e.printStackTrace();
            }
        }
    }

    private void delay(double d) throws InterruptedException {
        Thread.sleep( (long)d );
    }



}
