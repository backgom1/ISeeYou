package prod.discord_bot.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RiotConfig {

    @Value("${riot.api.key}")
    private String apiKey;

    @Bean
    public RestClient baseClientV1() {
        return RestClient.builder()
                .baseUrl("https://asia.api.riotgames.com/")
                .defaultHeader("X-Riot-Token",apiKey)
                .build();
    }

    @Bean
    public RestClient baseClientV5() {
        return RestClient.builder()
                .baseUrl("https://kr.api.riotgames.com/")
                .defaultHeader("X-Riot-Token",apiKey)
                .build();
    }
}
