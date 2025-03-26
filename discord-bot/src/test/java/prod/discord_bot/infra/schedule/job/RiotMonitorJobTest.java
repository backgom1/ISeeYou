package prod.discord_bot.infra.schedule.job;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import prod.discord_bot.infra.repository.ChannelUserRepository;
import prod.discord_bot.infra.repository.RiotApiRepositoryV2;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;


@ExtendWith(MockitoExtension.class)
class RiotMonitorJobTest {

    @Mock
    private JDA mockJda;

    @Mock
    private TextChannel mockTextChannel;

    @InjectMocks
    private RiotMonitorJob job;

    @Mock
    private JobExecutionContext context;

    @Mock
    private ChannelUserRepository channelUserRepository;

    @Test
    @DisplayName("모니터링 스케줄링 동작이 재대로 되는지 검증하는 테스트")
    public void testExecute_withValidData() {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("channelId", "1234567890");
        when(context.getMergedJobDataMap()).thenReturn(dataMap);
        when(channelUserRepository.findMonitorUsersByChannelId("1234567890"))
                .thenReturn(List.of());

        job.execute(context);

        verify(mockTextChannel, atLeastOnce()).sendMessage(anyString());
    }
}