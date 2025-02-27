package prod.discord_bot.infra.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.dto.spectator.request.SpectatorRequest;
import prod.discord_bot.dto.spectator.response.NotPlayingGameResponse;
import prod.discord_bot.infra.config.RiotConfig;
import prod.discord_bot.presentation.exception.NotPlayingGameException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
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

    @Mock
    private SpectatorDto mockSpectator;


    @Test
    @DisplayName("소환사 명과 태그를 입력하면 puuid,gameName,tagLine을 반환한다.")
    void shouldReturnMockedAccountResponse() {
        when(riotConfig.baseClientV1()).thenReturn(restClient);
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

    @Test
    @DisplayName("현재 게임을 진행 중인지 검증한다.")
    void shouldPlayingGame() {
        // given
        String gameId = UUID.randomUUID().toString();
        SpectatorRequest request = new SpectatorRequest(gameId);

        when(riotConfig.baseClientV5()).thenReturn(restClient);

        when(mockSpectator.getGameMode()).thenReturn("MATCHED");
        when(mockSpectator.getGameLength()).thenReturn(1230133);

        when(restClient.get()
                .uri(eq("lol/spectator/tft/v5/active-games/by-puuid/{encryptedPUUID}"), eq(request.getEncryptedPUUID()))
                .retrieve()
                .onStatus(any(), any())
                .body(SpectatorDto.class))
                .thenReturn(mockSpectator);

        // when
        SpectatorDto spectatorGame = riotApiRepository.getSpectatorGame(request);

        // then
        assertThat(spectatorGame).isNotNull();
        assertThat(spectatorGame.getGameMode()).isEqualTo("MATCHED");
        assertThat(spectatorGame.getGameLength()).isEqualTo(1230133);

    }

    @Test
    @DisplayName("현재 게임을 미 진행 중인지 검증한다.")
    void shouldNotPlayingGame() {
        //given
        String gameId = UUID.randomUUID().toString();
        SpectatorRequest request = new SpectatorRequest(gameId);
        when(riotConfig.baseClientV5()).thenReturn(restClient);
        when(restClient.get()
                .uri(eq("lol/spectator/tft/v5/active-games/by-puuid/{encryptedPUUID}"), eq(request.getEncryptedPUUID()))
                .retrieve()
                .onStatus(any(),any())
                .body(SpectatorDto.class))
                .thenThrow(new NotPlayingGameException());

        //when&then
        assertThatThrownBy(() -> riotApiRepository.getSpectatorGame(request))
                .isInstanceOf(NotPlayingGameException.class);

    }
}