package http;

import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import player.Player;
import player.Players;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.*;

public class HttpClientShould {
    @Test
    void call_web_server_and_request_information() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://sevsenlearn.free.beeceptor.com/players"))
                .build();
        final HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        System.out.println(body);

        Gson gson = new Gson();
        Players players = gson.fromJson(body, Players.class);
        players.getPlayers().forEach(System.out::println);

        List<Player> collect = players.getPlayers().stream()
                .filter(goal -> goal.getGoal() < 225)
                .collect(toUnmodifiableList());
        System.out.println(collect);

        assertThat(collect).contains(new Player("Ali karimi", 250));
    }
}
