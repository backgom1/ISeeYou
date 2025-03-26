package prod.discord_bot.application;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import prod.discord_bot.domain.DiscordMonitorDomainService;
import prod.discord_bot.domain.NowPlayMonitoringDomainService;
import prod.discord_bot.dto.DiscordMessageResult;
import prod.discord_bot.presentation.exception.DuplicateChannelException;


@Service
@RequiredArgsConstructor
public class DiscordCommandService extends ListenerAdapter {

    private final DiscordMonitorDomainService discordMonitorDomainService;
    private final NowPlayMonitoringDomainService nowPlayMonitoringDomainService;


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        //느낌표가 있으면 동작하지 않도록 설정해야함

        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        if (message.startsWith("!감시")) {
            String channelId = event.getChannel().getId();
            DiscordMessageResult<Void> result = discordMonitorDomainService.startMonitoring(message, channelId);
            sendMessage(event, result.getMessage());
        } else if (message.startsWith("!목록")) {
            String channelId = event.getChannel().getId();
            DiscordMessageResult<String> result = discordMonitorDomainService.findMonitorUsersByChannelId(channelId);
            sendMessage(event, result.getData());
        } else if (message.startsWith("!모니터링")) {
            String[] command = message.split(" ");
            String action = command[1];
            String channelId = event.getChannel().getId();
            boolean found = nowPlayMonitoringDomainService.hasMonitoringSchedule(channelId);
            if (found) {
                throw new DuplicateChannelException();
            }
            if ("시작".equals(action)) {
                nowPlayMonitoringDomainService.startMonitoring(channelId);
                sendMessage(event, "감시를 시작합니다.");
            } else if ("종료".equals(action)) {
                nowPlayMonitoringDomainService.shutdownMonitoring(channelId);
                sendMessage(event, "감시를 종료합니다.");
            } else {
                sendMessage(event, "존재하지 않는 명령어입니다.");
            }

        } else {
            sendMessage(event, "존재하지 않는 명령어입니다.");
        }


    }

    private void sendMessage(@NotNull MessageReceivedEvent event, String text) {
        event.getChannel().sendMessage(text).queue();
    }

}
