package prod.discord_bot.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import prod.discord_bot.domain.DiscordMonitorDomainService;
import prod.discord_bot.domain.NowPlayMonitoringDomainService;
import prod.discord_bot.dto.DiscordMessageResult;
import prod.discord_bot.infra.discord.config.DiscordBotStarter;


@Service
@RequiredArgsConstructor
public class DiscordCommandService extends ListenerAdapter {

    private final DiscordBotStarter discordBotStarter;
    private final DiscordMonitorDomainService discordMonitorDomainService;
    private final NowPlayMonitoringDomainService nowPlayMonitoringDomainService;

    @PostConstruct
    public void init() {
        discordBotStarter.startBot(this);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        if (message.startsWith("!감시")) {
            String channelId = event.getChannel().getId();
            DiscordMessageResult<Void> result = discordMonitorDomainService.startMonitoring(message, channelId);

            event.getChannel()
                    .sendMessage(result.getMessage())
                    .queue();

        } else if (message.equals("!목록")) {
            String channelId = event.getChannel().getId();
            DiscordMessageResult<String> result = discordMonitorDomainService.findMonitorUsersByChannelId(channelId);

            event.getChannel()
                    .sendMessage(result.getData())
                    .queue();

        } else if (message.equals("!모니터링")) {
            String[] command = message.split(" ");
            String action = command[1];
            String channelId = event.getChannel().getId();
            if ("시작".equals(action)) {
                nowPlayMonitoringDomainService.startMonitoring(channelId);
                event.getChannel().sendMessage("감시를 시작합니다.").queue();
            } else if ("종료".equals(action)) {
                nowPlayMonitoringDomainService.shutdownMonitoring(channelId);
                event.getChannel().sendMessage("감시를 종료합니다.").queue();
            } else {
                event.getChannel().sendMessage("존재하지 않는 명령어입니다.").queue();
            }

        } else {
            event.getChannel().sendMessage("존재하지 않는 명령어입니다.").queue();
        }


    }

}
