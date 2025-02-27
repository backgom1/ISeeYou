package prod.discord_bot.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TFTUserInfoDto {

    private final String userId;
    private final String userTag;
    private final int summonerLevel;
    private final int win;
    private final int losses;
    private final String tier;
    private final String rank;
    private final int leaguePoints;
    private final LocalDateTime regDate;
    private final LocalDateTime updateDate;

     TFTUserInfoDto(String userId, String userTag, int summonerLevel, int win, int losses, String tier, String rank, int leaguePoints, LocalDateTime regDate, LocalDateTime updateDate) {
        this.userId = userId;
        this.userTag = userTag;
        this.summonerLevel = summonerLevel;
        this.win = win;
        this.losses = losses;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }
}
