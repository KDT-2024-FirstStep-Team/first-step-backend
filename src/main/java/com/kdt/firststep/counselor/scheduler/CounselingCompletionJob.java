package com.kdt.firststep.counselor.scheduler;

import com.kdt.firststep.counselor.service.CounselingService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution // 동시 실행 방지
public class CounselingCompletionJob implements Job {
    private final CounselingService counselingService;
    private final Logger logger = LoggerFactory.getLogger(CounselingCompletionJob.class);

    public CounselingCompletionJob(CounselingService counselingService) {
        this.counselingService = counselingService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("Starting counseling completion check job");
            counselingService.completeExpiredReservations();
            logger.info("Completed counseling completion check job");
        } catch (Exception e) {
            logger.error("Error during counseling completion job", e);
            throw new JobExecutionException(e);
        }
    }
}
