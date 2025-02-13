package prod.discord_bot.dto;

import java.time.LocalDateTime;

public class TFTUserInfoDto {

    private String userId;

    private String userTag;

    private int summonerLevel;

    private int win;

    private int losses;

    private String tier;

    private String rank;

    private int leaguePoints;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

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
