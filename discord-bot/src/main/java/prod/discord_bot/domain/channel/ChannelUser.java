package prod.discord_bot.domain.channel;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import prod.discord_bot.domain.global.BaseEntity;
import prod.discord_bot.domain.user.UserMonitor;

@Entity
@NoArgsConstructor
@Table(name = "channel_user")
public class ChannelUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_user_id")
    private Long id;

    @Column(name = "channel_id")
    private String channelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_monitor_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserMonitor userMonitor;

    private ChannelUser(String channelId, UserMonitor userMonitor) {
        this.channelId = channelId;
        this.userMonitor = userMonitor;
    }

    public static ChannelUser create(String channelId, UserMonitor userMonitor) {
        return new ChannelUser(channelId, userMonitor);
    }
}
