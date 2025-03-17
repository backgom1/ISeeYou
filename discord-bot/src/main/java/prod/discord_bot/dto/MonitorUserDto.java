package prod.discord_bot.dto;


import lombok.Getter;

@Getter
public class MonitorUserDto {
    private String userId;
    private String userTag;

    public MonitorUserDto(String userId, String userTag) {
        this.userId = userId;
        this.userTag = userTag;
    }
}
