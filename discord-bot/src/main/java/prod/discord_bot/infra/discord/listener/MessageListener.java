package prod.discord_bot.infra.discord.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        // 봇이 보낸 메시지는 무시
        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        // "!ping" 입력 시 "Pong!" 응답
        if (message.equalsIgnoreCase("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        }

        // "!hello" 입력 시 사용자 태그와 함께 인사
        if (message.equalsIgnoreCase("!hello")) {
            String userTag = event.getAuthor().getAsTag();
            event.getChannel().sendMessage("안녕하세요, " + userTag + "님! 👋").queue();
        }
    }

}
