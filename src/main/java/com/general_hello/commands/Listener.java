package com.general_hello.commands;

import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.Storing.Data;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Listener extends ListenerAdapter {
    private final CommandManager manager;
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    public static HashMap<String, Integer> count = new HashMap<>();

    public Listener(EventWaiter waiter) {
        manager = new CommandManager(waiter);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onReconnected(@NotNull ReconnectedEvent event) {
        LOGGER.info("{} is reconnected!! Response number {}", event.getJDA().getSelfUser().getAsTag(), event.getResponseNumber());
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        LOGGER.info(event.getAuthor().getName());

        LOGGER.info(event.getMessage().getContentRaw() + "\n" +
                "Sent by " +
                event.getAuthor().getName() + " in " +
                event.getGuild().getName());

        final long guildID = event.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));
        String raw = event.getMessage().getContentRaw();

        try {
            if ((Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(2)) >= (Data.user.get(event.getMember().getId()).get(0)) && (Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(2)) < Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(3) && (Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(2)) < Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(4)) {
                Integer s = Data.user.get(event.getMember().getId()).get(2);
                Integer lol = Data.user.get(event.getMember().getId()).get(0);
                ArrayList<Integer> stats = Data.user.get(event.getMember().getId());
                stats.set(0, s);
                Integer HP = stats.get(1);
                stats.set(1, HP + (2 * (s-lol)));
            }

            if (Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(3) >= (Data.user.get(event.getMember().getId()).get(0)) && (Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(3)) < Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(2) && (Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(3)) < Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(4)) {
                Integer s = Data.user.get(event.getMember().getId()).get(3);
                Integer lol = Data.user.get(event.getMember().getId()).get(0);
                ArrayList<Integer> stats = Data.user.get(event.getMember().getId());
                stats.set(0, s);
                Integer HP = stats.get(1);
                stats.set(1, HP + (2 * (s-lol)));
            }

            if ((Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(4)) >= (Data.user.get(event.getMember().getId()).get(0)) && (Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(4)) < Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(3) && (Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(4)) < Data.user.get(Objects.requireNonNull(event.getMember()).getId()).get(2)) {
                Integer s = Data.user.get(event.getMember().getId()).get(4);
                ArrayList<Integer> stats = Data.user.get(event.getMember().getId());
                Integer lol = Data.user.get(event.getMember().getId()).get(0);
                stats.set(0, s);
                Integer HP = stats.get(1);
                stats.set(1, HP + (2 * (s-lol)));
            }
        } catch (Exception ignored) {}

        try {
            if (LocalDateTime.now().isAfter(Data.raidTime.get(event.getChannel()))) {
                event.getChannel().sendMessage("The raid has already ended and we failed to defeat the monster :( !").queue();
                Data.raidMembers.remove(event.getChannel());
                Data.raidMonster.remove(event.getChannel());
                Data.raidTime.remove(event.getChannel());
                return;
            }
        } catch (Exception ignored) {}

        if (raw.equalsIgnoreCase(prefix + "shutdown") && event.getAuthor().getId().equals(Config.get("owner_id"))) {
            shutdown(event, true);
            return;
        } else if (raw.equalsIgnoreCase(prefix + "shutdown") && event.getAuthor().getId().equals(Config.get("owner_id_partner"))) {
            shutdown(event, false);
            return;
        }

        if (raw.startsWith(prefix)) {
            try {
                manager.handle(event, prefix);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String commandsCount() {
        int x = 0;
        int size = CommandManager.commandNames.size();
        StringBuilder result = new StringBuilder();

        while (x < size) {
            String commandName = CommandManager.commandNames.get(x);
            result.append(x+1).append(".) ").append(commandName).append(" - ").append(count.get(commandName)).append("\n");
            x++;
        }

        return String.valueOf(result);
    }

    public static void shutdown(GuildMessageReceivedEvent event, boolean isOwner) {
        LOGGER.info("The bot " + event.getAuthor().getAsMention() + " is shutting down.\n" +
                "Thank you for using General_Hello's Code!!!");

        event.getChannel().sendMessage("Shutting down...").queue();
        event.getChannel().sendMessage("Bot successfully shutdown!!!!").queue();
        EmbedBuilder em = new EmbedBuilder().setTitle("Shutdown details!!!!").setColor(Color.red).setFooter("Shutdown on ").setTimestamp(LocalDateTime.now());
        em.addField("Shutdown made by ", event.getAuthor().getName(), false);
        em.addField("Date", LocalDateTime.now().getDayOfWeek().name(), false);
        em.addField("List of Commands used in this session....", commandsCount(), false);
        event.getAuthor().openPrivateChannel().complete().sendMessage(em.build()).queue();

        if (!isOwner) {
            User owner = event.getJDA().retrieveUserById(Config.get("owner_id")).complete();
            owner.openPrivateChannel().complete().sendMessage(em.build()).queue();
        }

        event.getJDA().shutdown();
        BotCommons.shutdown(event.getJDA());
    }
}
