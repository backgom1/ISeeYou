package prod.discord_bot.infra.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.infra.config.RiotConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiotApiRepositoryTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient restClient;

    @Mock
    private RiotConfig riotConfig;

    @InjectMocks
    private RiotApiRepository riotApiRepository;

    @BeforeEach
    void setUp() {
        when(riotConfig.baseClient()).thenReturn(restClient);
    }

    @Test
    void shouldReturnMockedAccountResponse() {
        // given
        AccountRequest request = new AccountRequest("SummonerName", "KR1");
        AccountDto mockResponse = new AccountDto("12345", "SummonerName", "KR1");


        when(restClient.get()
                .uri(eq("riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}"),
                        eq("SummonerName"),
                        eq("KR1"))
                .retrieve()
                .body(eq(AccountDto.class)))
                .thenReturn(mockResponse);

        // when
        AccountDto result = riotApiRepository.getAccountByUsername(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getPuuid()).isEqualTo("12345");
        assertThat(result.getGameName()).isEqualTo("SummonerName");
    }
}