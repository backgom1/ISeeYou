package prod.discord_bot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeagueEntryDto {
    private String puuid;
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private String summonerId;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean veteran;
    private boolean inactive;
    private boolean freshBlood;
    private boolean hotStreak;
}
