package co.pivoterra.command;

import java.awt.Color;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import co.pivoterra.command.defaults.CommandExecutor;
import co.pivoterra.command.defaults.CommandInfo;
import co.pivoterra.command.defaults.CommandSender;
import co.pivoterra.repository.UserRepository;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import co.pivoterra.entity.impl.UserEntityImpl;

@Component
public class RegisterAccountCommand implements CommandExecutor {

  private final UserRepository userRepository;

  @Autowired
  public RegisterAccountCommand(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public RegisterAccountCommand() {
    this(null);
  }

  @CommandInfo("register")
  @Override
  public void execute(CommandSender commandSender, List<String> args) {
    long userId = commandSender.getJdaUser().getIdLong();

    Optional<UserEntityImpl> optionalUserEntity = userRepository.findById(userId);

    if (optionalUserEntity.isPresent()) {
      String footer = commandSender.getMessage("already-registered");

      MessageEmbed messageEmbed = new EmbedBuilder()
          .setColor(Color.RED)
          .setFooter(footer, null)
          .build();

      commandSender.sendEmbedMessage(messageEmbed);
      return;
    }

    User jdaUser = commandSender.getJdaUser();

    UserEntityImpl userEntity = new UserEntityImpl.Builder()
        .withIdentifier(jdaUser.getIdLong())
        .withName(jdaUser.getName())
        .setRegistrationDate(Date.from(Instant.now()))
        .build();

    this.userRepository.save(userEntity);

    String title = commandSender.getMessage("successfully-registered");
    String nickname = commandSender.getMessage("nickname");
    String identifier = commandSender.getMessage("identifier");
    String date = commandSender.getMessage("date");

    MessageEmbed messageEmbed = new EmbedBuilder()
        .setColor(Color.GREEN)
        .setTitle(title)
        .addField(nickname, userEntity.getName(), true)
        .addField(identifier, userEntity.getIdentifier().toString(), true)
        .addField(date, userEntity.getRegistrationDate().toString(), true)
        .build();

    commandSender.sendEmbedMessage(messageEmbed);
  }

}
