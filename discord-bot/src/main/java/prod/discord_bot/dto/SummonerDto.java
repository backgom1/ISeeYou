package prod.discord_bot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SummonerDto {
    private String id;
    private String accountId;
    private String puuid;
    private int profileIconId;
    private long revisionDate;
    private long summonerLevel;
}
