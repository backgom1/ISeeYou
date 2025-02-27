package prod.discord_bot.infra.discord.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.dto.spectator.request.SpectatorRequest;
import prod.discord_bot.infra.repository.RiotApiRepository;
import prod.discord_bot.presentation.exception.NotPlayingGameException;


@Component
public class MessageListener extends ListenerAdapter {

    private final RiotApiRepository riotApiRepository;

    @Autowired
    public MessageListener(RiotApiRepository riotApiRepository) {
        this.riotApiRepository = riotApiRepository;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        if (message.startsWith("!감시")) {
            String[] parts = message.split(" ");

            if (parts.length < 2) {
                event.getChannel().sendMessage("사용법: `!감시 게임이름#태그`").queue();
                return;
            }

            String riotId = parts[1];
            String[] riotIdParts = riotId.split("#");

            if (riotIdParts.length < 2) {
                event.getChannel().sendMessage("올바른 형식: `게임이름#태그`를 입력해주세요 로로야!").queue();
                return;
            }

            String gameName = riotIdParts[0];
            String tagLine = riotIdParts[1];

            try {
                AccountDto account = riotApiRepository.getAccountByUsername(new AccountRequest(gameName, tagLine));
                SpectatorDto game = riotApiRepository.getSpectatorGame(new SpectatorRequest(account.getPuuid()));

                event.getChannel()
                        .sendMessage("게임 타입: " + game.getGameType() + "\n" +
                                "게임 진행 시간: " + game.getGameLength() + "초")
                        .queue();
            } catch (NotPlayingGameException e) {
                event.getChannel()
                        .sendMessage(e.getMessage())
                        .queue();
            }

        }

    }

}
