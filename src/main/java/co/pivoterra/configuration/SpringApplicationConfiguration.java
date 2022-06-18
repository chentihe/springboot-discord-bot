package co.pivoterra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.pivoterra.command.defaults.impl.CommandRegistry;
import co.pivoterra.component.MessageComponent;
import co.pivoterra.service.GarbanzoService;
import co.pivoterra.service.impl.GarbanzoServiceImpl;

@Configuration
public class SpringApplicationConfiguration {

  private final MessageComponent messageComponent;
  private final GarbanzoProperties garbanzoProperties;

  public SpringApplicationConfiguration(MessageComponent messageComponent,
      GarbanzoProperties garbanzoProperties) {
    this.messageComponent = messageComponent;
    this.garbanzoProperties = garbanzoProperties;
  }

  @Bean
  public GarbanzoService garbanzoService() {
    return new GarbanzoServiceImpl();
  }

  @Bean
  public CommandRegistry commandRegistryBean() {
    return new CommandRegistry(this.messageComponent, this.garbanzoService(),
        this.garbanzoProperties);
  }

}
