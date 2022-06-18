package co.pivoterra;

import co.pivoterra.command.defaults.impl.CommandRegistry;
import co.pivoterra.listener.GuildMemberJoinListener;
import co.pivoterra.service.GarbanzoService;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import co.pivoterra.command.CommitCommand;
import co.pivoterra.command.EightBallCommand;
import co.pivoterra.command.FactCommand;
import co.pivoterra.command.GitHubCommand;
import co.pivoterra.command.RegisterAccountCommand;
import co.pivoterra.command.SayCommand;

@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap implements CommandLineRunner {

  private final GarbanzoService garbanzoService;
  private final CommandRegistry commandRegistry;
  private final EventWaiter eventWaiter = new EventWaiter();

  public ApplicationBootstrap(GarbanzoService garbanzoService, CommandRegistry commandRegistry) {
    this.garbanzoService = garbanzoService;
    this.commandRegistry = commandRegistry;
  }

  public static void main(String[] args) {
    SpringApplication.run(ApplicationBootstrap.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    this.garbanzoService.startBot();

    this.garbanzoService.registerListeners(
        new GuildMemberJoinListener(), eventWaiter
    );

    this.commandRegistry.registerByExecutors(
        new RegisterAccountCommand(),
        new GitHubCommand(),
        new CommitCommand(),
        new SayCommand(),
        new FactCommand(),
        new EightBallCommand()
    );
  }

  public EventWaiter getEventWaiter() {
    return eventWaiter;
  }
}
