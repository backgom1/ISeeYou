package prod.discord_bot.application;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import prod.discord_bot.application.command.MessageCommand;
import prod.discord_bot.domain.DiscordMonitorDomainService;
import prod.discord_bot.domain.NowPlayMonitoringDomainService;
import prod.discord_bot.dto.DiscordMessageResult;
import prod.discord_bot.presentation.exception.DuplicateChannelException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DiscordCommandService extends ListenerAdapter {

    private final DiscordMonitorDomainService discordMonitorDomainService;
    private final NowPlayMonitoringDomainService nowPlayMonitoringDomainService;

    private final List<MessageCommand> commands = List.of(
            new MessageCommand("!감시", "유저를 감시 리스트에 추가합니다. 예: `!감시 유저이름#태그명`", this::handleMonitorStart),
            new MessageCommand("!제거", "유저를 감시 리스트에 제거합니다. 예: `!제거 유저이름#태그명`", this::handleMonitorEnd),
            new MessageCommand("!목록", "현재 감시 중인 유저 목록을 확인합니다.", this::handleMonitorList),
            new MessageCommand("!모니터링", "지금 실행 중인 감시를 시작/종료합니다. 예: `!모니터링 시작/종료`", this::handleNowMonitoring),
            new MessageCommand("!도움", "사용 가능한 명령어 목록을 보여줍니다.", this::handleHelpMenu)
    );

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;
        String message = event.getMessage().getContentRaw();

        commands.stream()
                .filter(cmd -> message.startsWith(cmd.getKeyword()))
                .findFirst()
                .ifPresentOrElse(
                        cmd -> cmd.getHandler().accept(event),
                        () -> sendMessage(event, "존재하지 않는 명령어입니다. `!도움` 명령어로 확인하세요.")
                );
    }

    // !감시 명령어 핸들러
    private void handleMonitorStart(MessageReceivedEvent event) {
        String channelId = event.getChannel().getId();
        DiscordMessageResult<Void> result = discordMonitorDomainService.startMonitoring(event.getMessage().getContentRaw(), channelId);
        sendMessage(event, result.getMessage());
    }

    // !종료 명령어 핸들러
    private void handleMonitorEnd(MessageReceivedEvent event) {
        String channelId = event.getChannel().getId();
        DiscordMessageResult<Void> result = discordMonitorDomainService.startMonitoring(event.getMessage().getContentRaw(), channelId);
        sendMessage(event, result.getMessage());
    }

    // !목록 명령어 핸들러
    private void handleMonitorList(MessageReceivedEvent event) {
        String channelId = event.getChannel().getId();
        DiscordMessageResult<String> result = discordMonitorDomainService.findMonitorUsersByChannelId(channelId);
        sendMessage(event, result.getData());
    }

    // !모니터링 명령어 핸들러
    private void handleNowMonitoring(MessageReceivedEvent event) {
        String[] command = event.getMessage().getContentRaw().split(" ");
        if (command.length < 2) {
            sendMessage(event, "명령어 형식이 올바르지 않습니다.");
            return;
        }
        String action = command[1];
        String channelId = event.getChannel().getId();
        boolean scheduleExists = nowPlayMonitoringDomainService.hasMonitoringSchedule(channelId);

        if ("시작".equals(action)) {
            if (scheduleExists) {
                throw new DuplicateChannelException();
            }
            nowPlayMonitoringDomainService.startMonitoring(channelId);
            sendMessage(event, "감시를 시작합니다.");
        } else if ("종료".equals(action)) {
            nowPlayMonitoringDomainService.shutdownMonitoring(channelId);
            sendMessage(event, "감시를 종료합니다.");
        } else {
            sendMessage(event, "존재하지 않는 명령어입니다.");
        }
    }

    public void handleHelpMenu(MessageReceivedEvent event) {
        StringBuilder help = new StringBuilder("사용 가능한 명령어:\n\n");
        for (MessageCommand cmd : commands) {
            help.append(cmd.getKeyword()).append(" - ").append(cmd.getDescription()).append("\n");
        }
        sendMessage(event, help.toString());
    }


    private void sendMessage(@NotNull MessageReceivedEvent event, String text) {
        event.getChannel().sendMessage(text).queue();
    }

}
