package prod.discord_bot.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prod.discord_bot.domain.global.BaseEntity;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.LeagueEntryDto;
import prod.discord_bot.dto.SummonerDto;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_monitor")
public class UserMonitor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monitor_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_tag")
    private String userTag;

    @Column(name = "summoner_level")
    private long summonerLevel;

    private int win;

    private int losses;

    private String tier;

    private String rank;

    @Column(name = "league_points")
    private int leaguePoints;


     private UserMonitor(String userId, String userTag, long summonerLevel, int win, int losses, String tier, String rank, int leaguePoints) {
        this.userId = userId;
        this.userTag = userTag;
        this.summonerLevel = summonerLevel;
        this.win = win;
        this.losses = losses;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
    }

    public static UserMonitor create(AccountDto account, SummonerDto tftSummoner, LeagueEntryDto dto){
         return new UserMonitor(account.getGameName(),account.getTagLine(),tftSummoner.getSummonerLevel(),dto.getWins(), dto.getLosses(), dto.getTier(), dto.getRank(), dto.getLeaguePoints());
    }
}
