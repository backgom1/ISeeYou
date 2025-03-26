package prod.discord_bot.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prod.discord_bot.domain.monitor.MonitoringSchedule;
import prod.discord_bot.infra.repository.MonitoringScheduleRepository;
import prod.discord_bot.infra.schedule.job.RiotMonitorJob;

@Slf4j
@Service
@RequiredArgsConstructor
public class NowPlayMonitoringDomainService {

    private final Scheduler scheduler;

    private final MonitoringScheduleRepository monitoringScheduleRepository;


    public boolean hasMonitoringSchedule(String channelId) {
        return monitoringScheduleRepository.existsByChannelId(channelId);
    }

    @Transactional
    public void startMonitoring(String channelId) {
        log.info("모니터링 감시 시작 -> {} ", channelId);

        try {
            JobDetail jobDetail = JobBuilder.newJob(RiotMonitorJob.class)
                    .withIdentity(channelId, "monitoring-jobs")
                    .usingJobData("channelId", channelId)
                    .storeDurably()
                    .build();

            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0 0/5 * * * ?");

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(channelId + "-trigger", "monitoring-triggers")
                    .forJob(jobDetail)
                    .withSchedule(cronSchedule)
                    .build();

            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        MonitoringSchedule monitoringSchedule = MonitoringSchedule.create(channelId);
        monitoringScheduleRepository.save(monitoringSchedule);

        log.info("모니터링 감시 시작 완료 -> {} ", channelId);
    }


    @Transactional
    public void shutdownMonitoring(String channelId) {
        log.info("모니터링 감시 종료 -> {} ", channelId);
        try {
            JobKey jobKey = new JobKey(channelId, "monitoring-jobs");

            boolean deleted = scheduler.deleteJob(jobKey);

            //TODO 종료를 뱉어 내는 로직이 필요함
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }


        monitoringScheduleRepository.deleteByChannelId(channelId);
        log.info("모니터링 감시 종료 완료 -> {} ", channelId);
    }
}
