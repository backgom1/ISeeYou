package prod.discord_bot.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prod.discord_bot.dto.TFTUserInfoDto;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tft_user_info")
public class TftUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_tag")
    private String userTag;

    @Column(name = "summoner_level")
    private int summonerLevel;

    private int win;

    private int losses;

    private String tier;

    private String rank;

    @Column(name = "league_points")
    private int leaguePoints;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

     private TftUserInfo(String userId, String userTag, int summonerLevel, int win, int losses, String tier, String rank, int leaguePoints, LocalDateTime regDate, LocalDateTime updateDate) {
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

    public static TftUserInfo of(TFTUserInfoDto request){
        return new TftUserInfo();
    }
}
