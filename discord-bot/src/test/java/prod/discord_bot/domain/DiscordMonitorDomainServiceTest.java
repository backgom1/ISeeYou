package prod.discord_bot.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prod.discord_bot.domain.channel.ChannelUser;
import prod.discord_bot.domain.user.UserMonitor;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.LeagueEntryDto;
import prod.discord_bot.dto.SummonerDto;
import prod.discord_bot.infra.repository.ChannelUserRepository;
import prod.discord_bot.infra.repository.UserMonitorRepository;
import prod.discord_bot.presentation.exception.MaxSetUserException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class DiscordMonitorDomainServiceTest {

    @Autowired
    private DiscordMonitorDomainService discordMonitorDomainService;

    @Autowired
    private ChannelUserRepository channelUserRepository;

    @Autowired
    private UserMonitorRepository userMonitorRepository;

    @BeforeEach
    void setUp() {
        UserMonitor userMonitor1 = UserMonitor.create(new AccountDto("123","흰오목눈이","KR1") ,new SummonerDto("123","3123123","123",12341,123412313,1000), new LeagueEntryDto("123","1234123","RANK","IV","kdjwjdiq","dqwdw",100,100,100,false,false,false,false));
        UserMonitor userMonitor2 = UserMonitor.create(new AccountDto("124","흰오목눈이","KR2") ,new SummonerDto("123","3123123","123",12341,123412313,1000), new LeagueEntryDto("123","1234123","RANK","IV","kdjwjdiq","dqwdw",100,100,100,false,false,false,false));
        UserMonitor userMonitor3 = UserMonitor.create(new AccountDto("125","흰오목눈이","KR3") ,new SummonerDto("123","3123123","123",12341,123412313,1000), new LeagueEntryDto("123","1234123","RANK","IV","kdjwjdiq","dqwdw",100,100,100,false,false,false,false));
        UserMonitor userMonitor4 = UserMonitor.create(new AccountDto("126","흰오목눈이","KR4") ,new SummonerDto("123","3123123","123",12341,123412313,1000), new LeagueEntryDto("123","1234123","RANK","IV","kdjwjdiq","dqwdw",100,100,100,false,false,false,false));
        UserMonitor userMonitor5 = UserMonitor.create(new AccountDto("127","흰오목눈이","KR5") ,new SummonerDto("123","3123123","123",12341,123412313,1000), new LeagueEntryDto("123","1234123","RANK","IV","kdjwjdiq","dqwdw",100,100,100,false,false,false,false));

        userMonitorRepository.saveAll(List.of(userMonitor1, userMonitor2, userMonitor3, userMonitor4, userMonitor5));

        ChannelUser channelUser1 = ChannelUser.create("1", userMonitor1);
        ChannelUser channelUser2 = ChannelUser.create("1", userMonitor1);
        ChannelUser channelUser3 = ChannelUser.create("1", userMonitor1);
        ChannelUser channelUser4 = ChannelUser.create("1", userMonitor1);
        ChannelUser channelUser5 = ChannelUser.create("1", userMonitor1);

        channelUserRepository.saveAll(List.of(channelUser1, channelUser2, channelUser3, channelUser4, channelUser5));

    }

    @AfterEach
    void tearDown() {
        channelUserRepository.deleteAllInBatch();
        userMonitorRepository.deleteAllInBatch();
    }

    @DisplayName("한 채널의 감시 사용자가 5명이 넘어가면 사용할 수 없다.")
    @Test
    void cannotAddWatcherWhenChannelIsFull() {
        assertThatThrownBy(()->  discordMonitorDomainService.startMonitoring("테스트용","1"))
                .isInstanceOf(MaxSetUserException.class)
                .hasMessage("채널당 사용자는 5명 이상을 넘을 수 없습니다.");
    }
}