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
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.MonitorUserDto;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.infra.repository.ChannelUserRepository;
import prod.discord_bot.infra.repository.RiotApiRepositoryV2;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
    private  RiotApiRepositoryV2 riotApiRepositoryV2;

    @Mock
    private ChannelUserRepository channelUserRepository;

    @Test
    @DisplayName("모니터링 스케줄링 동작이 재대로 되는지 검증하는 테스트")
    public void testExecute_withValidData() {

        //given
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("channelId", "1234567890");
        AccountDto mockAccount = new AccountDto("somePuuid", "닉네임", "태그");
        SpectatorDto mockGame = new SpectatorDto();


        //when
        when(context.getMergedJobDataMap()).thenReturn(dataMap);
        when(channelUserRepository.findMonitorUsersByChannelId("1234567890"))
                .thenReturn(List.of(new MonitorUserDto("흰오목눈이","Bird")));
        when(mockJda.getTextChannelById("1234567890")).thenReturn(mockTextChannel);
        when(mockTextChannel.sendMessage(anyString())).thenReturn(mock());
        when(riotApiRepositoryV2.getAccountByUsername(any())).thenReturn(mockAccount);
        when(riotApiRepositoryV2.getSpectatorGame(any())).thenReturn(mockGame);
        job.execute(context);


        //then
        verify(mockTextChannel, atLeastOnce()).sendMessage(anyString());
        verify(riotApiRepositoryV2, times(1)).getAccountByUsername(any());
        verify(riotApiRepositoryV2, times(1)).getSpectatorGame(any());
    }
}