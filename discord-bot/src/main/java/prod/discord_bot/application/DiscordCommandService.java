package prod.discord_bot.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import prod.discord_bot.domain.DiscordMonitorDomainService;
import prod.discord_bot.dto.StartMonitoringResult;
import prod.discord_bot.infra.discord.config.DiscordBotStarter;
import prod.discord_bot.presentation.exception.MaxSetUserException;


@Service
@RequiredArgsConstructor
public class DiscordCommandService extends ListenerAdapter {

    private final DiscordBotStarter discordBotStarter;
    private final DiscordMonitorDomainService discordMonitorDomainService;

    @PostConstruct
    public void init() {
        discordBotStarter.startBot(this);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {


        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        if (message.startsWith("!감시")) {
        }


        if (message.startsWith("!감시")) {
            String channelId = event.getChannel().getId();
            StartMonitoringResult result = discordMonitorDomainService.startMonitoring(message, channelId);

            event.getChannel()
                    .sendMessage(result.getMessage())
                    .queue();
        }

    }

}
