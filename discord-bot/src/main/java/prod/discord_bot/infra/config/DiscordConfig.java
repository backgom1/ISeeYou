package prod.discord_bot.infra.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prod.discord_bot.application.DiscordCommandService;

@Configuration
public class DiscordConfig {

    @Value("${discord.token}")
    private String token;

    @Bean
    public JDA jda(DiscordCommandService discordCommandService) throws Exception {
        return JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.playing("감시 중 🚀"))
                .addEventListeners(discordCommandService)
                .build()
                .awaitReady();
    }

}
