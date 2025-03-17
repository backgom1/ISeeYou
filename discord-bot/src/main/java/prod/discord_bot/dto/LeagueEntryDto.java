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
    private String summonerId;
    private String queueType;
    private String ratedTier;
    private int ratedRating;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean hotStreak;
    private boolean veteran;
    private boolean freshBlood;
    private boolean inactive;
    private MiniSeriesDTO miniSeries;
}
