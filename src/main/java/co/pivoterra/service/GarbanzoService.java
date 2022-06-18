package co.pivoterra.service;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import org.springframework.stereotype.Service;

@Service
public interface GarbanzoService {

  void startBot() throws LoginException, InterruptedException;

  void shutdownBot();

  void registerListeners(Object... listeners);

  JDA getJda();

}
