package prod.discord_bot.infra.discord.config;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import prod.discord_bot.infra.discord.listener.MessageListener;

import javax.security.auth.login.LoginException;

@Component
public class DiscordBot extends ListenerAdapter {

    @Value("${discord.token}") // application.properties에 설정
    private String token;

    private JDA jda;

    private final MessageListener messageListener;

    public DiscordBot(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @PostConstruct
    public void startBot() throws LoginException {
        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .setStatus(OnlineStatus.ONLINE) // 봇을 온라인 상태로 유지
                    .setActivity(Activity.playing("감시 중 🚀")) // 봇 활동 설정
                    .addEventListeners(messageListener) // 이벤트 리스너 추가
                    .build()
                    .awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
