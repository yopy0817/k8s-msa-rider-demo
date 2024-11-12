package com.coding404.simulator.component;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Log4j2
@Component
public class PositionSimulator implements Runnable {

    @Autowired
    private RabbitTemplate rabbitTemplate; //메시지큐
    @Value("${position.queue}")
    private String queueName; //전달할 큐이름

    @Override
    public void run() {

        try {
            this.startSimulator();
        } catch (Exception e) {
            //에러시에 인터럽트 - 예기치 않은 상황에서 인터럽트 시키고, 다시 복귀가능
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

    //파일 읽기
    public void startSimulator() throws InterruptedException  {

        Map<String, List<String>> map = setUpData(); //데이터 처리
        ExecutorService threadPool = Executors.newCachedThreadPool(); //비동기 쓰레드

        List<Callable<Object>> callableList = new ArrayList<>();

        for(String vehicleName : map.keySet()) {
            callableList.add( new Rider(
                                vehicleName,
                                map.get(vehicleName),
                                queueName,
                                rabbitTemplate)
                                );
        }

        //calls 리스트에 있는 모든 Callable 작업을 병렬로 실행합니다.
        //각 작업은 call() 메서드를 호출하여 차량의 위치 보고서를 큐에 전송
        //List<Future<Object>> result = threadPool.invokeAll(callableList, 10, TimeUnit.SECONDS); //InterruptedException
        threadPool.invokeAll(callableList); //callable이 종료전까지 다음으로 넘어가지 않습니다


        //한개씩종료
//        for(Future<Object> item : result ) {
//            item.cancel( true );
//        }
        //한번에 종료
//        threadPool.shutdownNow();
//        log.info("쓰레드 종료되었습니다");

    }

    //파일 읽기, 데이터 처리
    public Map<String, List<String>> setUpData() {
        Map<String, List<String>> map = new HashMap<>(); //라이더명, 라이더위치
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources("rider/*");

            for(Resource resource : resources ) { //리소스 파일을 가져와서 반복문

                List<String> list = new ArrayList<>(); //위치 저장 리스트
                String vehicleName = resource.getFilename().replace(".txt", ""); //파일명
                BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));

                String str = null;
                while( (str =  br.readLine() ) != null ) { //한줄씩 읽어서 저장
                    list.add(str);
                }

                map.put(vehicleName, list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }



}
