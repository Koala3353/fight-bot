package com.general_hello.commands.commands.SimpleCommands;

import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.Storing.Data;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddUserCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getId().equals("247907491436822528") && !ctx.getMember().getId().equals("268271834552598528") && !ctx.getMember().getId().equals("755975812909367387")) {
            ctx.getChannel().sendMessage("You don't have the permission to do this command!!!").queue();
            return;
        }

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("No details for the user placed!!!\n" + getHelp(prefix)).queue();
            return;
        }

        try {
            if (ctx.getArgs().get(1).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the user placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(2).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the user placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(3).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the user placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(4).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the user placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(5).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the user placed!!!\n" + getHelp(prefix)).queue();
                return;
            }
        } catch (Exception e) {
            ctx.getChannel().sendMessage("No details for the user placed!!!\n" + getHelp(prefix)).queue();
            return;
        }

        String userId;
        String level;
        String HP;
        String strength;
        String movement;
        String defense;

        try {
             userId = ctx.getMessage().getMentionedMembers().get(0).getId();
             level = ctx.getArgs().get(1);
             HP = ctx.getArgs().get(5);
             strength = ctx.getArgs().get(2);
             movement = ctx.getArgs().get(4);
             defense = ctx.getArgs().get(3);
        } catch (Exception e) {
            ctx.getChannel().sendMessage("You typed the parameters incorrectly").queue();
            return;
        }

        ArrayList<Integer> info = new ArrayList<>();
        info.add(0, Integer.valueOf(level));
        info.add(1, Integer.valueOf(HP));
        info.add(2, Integer.valueOf(strength));
        info.add(3, Integer.valueOf(movement));
        info.add(4, Integer.valueOf(defense));

        Data.user.put(userId, info);

        ctx.getChannel().sendMessage("User successfully added!!!").queue();

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Successfully created the user!!!").setColor(Color.cyan);
        embedBuilder.addField("User ID:", userId, false);
        embedBuilder.addField("User Name:", ctx.getJDA().retrieveUserById(userId).complete().getAsTag(), false);
        embedBuilder.addField("Level:", level, true);
        embedBuilder.addField("Strength:", strength, false);
        embedBuilder.addField("Defense:", defense, false);
        embedBuilder.addField("Movement:", movement, false);
        embedBuilder.addField("HP:", HP, false);
        embedBuilder.setTimestamp(LocalDateTime.now()).setFooter("See available commands with " + prefix + "help!!!");

        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "adduser";
    }

    @Override
    public String getHelp(String prefix) {
        return "Adds a user!!!\n" +
                "Format: `" + prefix + "adduser [user] [level] [str] [def] [mov] [HP]`\n" +
                "Example: `" + prefix + "adduser @Zoey 1 1 1 1 5`";
    }
}
