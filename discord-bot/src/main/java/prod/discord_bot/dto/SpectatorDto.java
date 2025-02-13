package prod.discord_bot.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SpectatorDto {
    private long gameId;
    private int mapId;
    private String gameMode;
    private String gameType;
    private int gameQueueConfigId;
    private List<ParticipantDto> participants;
    private ObserverDto observers;
    private String platformId;
    private List<BannedChampionDto> bannedChampions;
    private long gameStartTime;
    private int gameLength;

}
