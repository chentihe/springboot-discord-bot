package co.pivoterra.command;

import java.awt.Color;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.pivoterra.command.defaults.CommandExecutor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import co.pivoterra.command.defaults.CommandInfo;
import co.pivoterra.command.defaults.CommandSender;

@Component
public class SayCommand implements CommandExecutor {

  @CommandInfo(value = "say", minArguments = 1, maxArguments = Integer.MAX_VALUE, usage = "(?:{\"type\":\")(.*?)(?:\",.\"message\":\")(.*?)(?:\"})")
  @Override
  public void execute(CommandSender commandSender, List<String> args) {

    StringBuilder stringBuilder = new StringBuilder();

    for (String arg : args) {
      stringBuilder.append(arg).append(" ");
    }

    Pattern pattern = Pattern
        .compile("\\{\\s*\"type\"\\s*:\\s*(.*?)\\s*,\\s*\"message\"\\s*:\\s*(.*?)}");
    Matcher matcher = pattern.matcher(stringBuilder.toString());

    if (!matcher.find()) {
      this.performMessageFooter(commandSender, commandSender.getMessage("invalid-json"), Color.RED);
      return;
    }

    JSONObject jsonObject = new JSONObject(stringBuilder.toString());

    switch (jsonObject.getString("type")) {
      case "normal":
        commandSender.sendRawMessage(String.valueOf(jsonObject.get("message")));
        break;
      case "footer":
        this.performMessageFooter(commandSender, String.valueOf(jsonObject.get("message")),
            Color.decode("#2b2b2b"));
        break;
      default:
        this.performMessageFooter(commandSender, commandSender.getMessage("wrong-message-type"), Color.RED);
    }
  }

  private void performMessageFooter(CommandSender commandSender, String message, Color color) {
    MessageEmbed messageEmbed = new EmbedBuilder()
        .setColor(color)
        .setFooter(message, null)
        .build();

    commandSender.sendEmbedMessage(messageEmbed);
  }

}
