package co.pivoterra.events;

import co.pivoterra.ApplicationBootstrap;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

public class InteractionCreateEvent extends ListenerAdapter {
    private final ApplicationBootstrap app;

    public InteractionCreateEvent(ApplicationBootstrap app) {
        this.app = app;
    }

    @SubscribeEvent
    public void interactionCreate(ButtonInteractionEvent event) {
        final User user = event.getUser();
        event.reply("member:" + user.getId())
                .addActionRow(
                        Button
                          .link("FrontEndURL?member=${user}", "Connect Wallet")
                          .withStyle(ButtonStyle.LINK))
                .setEphemeral(true)
                .queue();
        // todo : how to get the request from frontend and reply followup msg to this interaction?
    }
}
