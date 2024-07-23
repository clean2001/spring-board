package com.beyond.board.post.service;

// batch를 돌리기 위한 스케줄러 정의
// batch job을 호출하는 스케쥴러 (executeJob 메서드를 호출할 것이다.)

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PostBatchScheduler {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private PostJobConfiguration postJobConfiguration;
    @Scheduled(cron = "0 0/1 * * * *")
    public void batchSchedule(){
//        PostJobConfiguration postJobConfiguration = new PostJobConfiguration();
        Map<String, JobParameter> configMap = new HashMap<>();
        configMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(configMap);
        try{
            jobLauncher.run(postJobConfiguration.excuteJob(), jobParameters);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
