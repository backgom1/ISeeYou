package prod.discord_bot.infra.discord.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import prod.discord_bot.application.DiscordCommandService;

@Slf4j
@Component
public class DiscordBotStarter extends ListenerAdapter {

    @Value("${discord.token}")
    private String token;

    private JDA jda;

    public void startBot(DiscordCommandService discordCommandService) {
        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.playing("감시 중 🚀"))
                    .addEventListeners(discordCommandService)
                    .build()
                    .awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
