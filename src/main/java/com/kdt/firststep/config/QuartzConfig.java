package com.kdt.firststep.config;

import com.kdt.firststep.counselor.scheduler.CounselingCompletionJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail counselingCompletionJobDetail() {  // 이름을 jobDetail로 변경
        return JobBuilder.newJob(CounselingCompletionJob.class)
                .withIdentity("counselingCompletionJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger counselingCompletionTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(counselingCompletionJobDetail())    // 변경된 메소드명 사용
                .withIdentity("counselingCompletionTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)
                        .repeatForever())
                .build();
    }
}