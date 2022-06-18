package co.pivoterra.service.impl;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import co.pivoterra.service.GarbanzoService;

public class GarbanzoServiceImpl implements GarbanzoService {

  private JDA jda;

  @Value("${jda.discord.token}")
  private String token;

  @Override
  public void startBot() throws LoginException, InterruptedException {
    this.jda = JDABuilder.create(token, GatewayIntent.GUILD_MEMBERS)
        .setEventManager(new AnnotatedEventManager()).build();

    getJda().awaitReady();
  }

  @Override
  public void shutdownBot() {
    this.jda.shutdown();
  }

  @Override
  public void registerListeners(Object... listeners) {
    this.jda.addEventListener(listeners);
  }

  @Override
  public JDA getJda() {
    return this.jda;
  }

}
