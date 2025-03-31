package prod.discord_bot.application.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.function.Consumer;

public class MessageCommand {

    private final String keyword;
    private final String description;
    private final Consumer<MessageReceivedEvent> handler;

    public MessageCommand(String keyword, String description, Consumer<MessageReceivedEvent> handler) {
        this.keyword = keyword;
        this.description = description;
        this.handler = handler;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDescription() {
        return description;
    }

    public Consumer<MessageReceivedEvent> getHandler() {
        return handler;
    }
}
