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

public class CreateMonsterCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getId().equals("247907491436822528") && !ctx.getMember().getId().equals("268271834552598528") && !ctx.getMember().getId().equals("755975812909367387")) {
            ctx.getChannel().sendMessage("You dont have the permission to do this command!!!").queue();
            return;
        }

        try {
            if (ctx.getArgs().isEmpty()) {
                ctx.getChannel().sendMessage("No details for the monster placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(1).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the monster placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(2).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the monster placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(3).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the monster placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(4).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the monster placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(5).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the monster placed!!!\n" + getHelp(prefix)).queue();
                return;
            }
        } catch (Exception e) {
            ctx.getChannel().sendMessage("No details for the monster placed!!!\n" + getHelp(prefix)).queue();
            return;
        }

        Integer monsterId = Integer.parseInt(ctx.getArgs().get(0));
        String monsterName = ctx.getArgs().get(1);

        if (Data.monster.containsKey(monsterId)) {
            ctx.getChannel().sendMessage("Monster with the same id created already!!!").queue();
            return;
        }

        String HP = ctx.getArgs().get(2);
        String strength = ctx.getArgs().get(3);
        String movement = ctx.getArgs().get(4);
        String defense = ctx.getArgs().get(5);

        ArrayList<String> info = new ArrayList<>();
        info.add(monsterName);
        info.add(HP);
        info.add(strength);
        info.add(movement);
        info.add(defense);

        Data.monster.put(monsterId, info);
        Data.monsterId.add(monsterId);

        ctx.getChannel().sendMessage("Monster successfully created!!!").queue();

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Successfully created the monster!!!").setColor(Color.cyan);
        embedBuilder.addField("Monster ID:", String.valueOf(monsterId), false);
        embedBuilder.addField("Name:", monsterName, true);
        embedBuilder.addField("HP:", HP, false);
        embedBuilder.addField("Strength:", strength, false);
        embedBuilder.addField("Movement:", movement, false);
        embedBuilder.addField("Defense:", defense, false);
        embedBuilder.setTimestamp(LocalDateTime.now()).setFooter("See available commands with " + prefix + "help!!!");

        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "createmonster";
    }

    @Override
    public String getHelp(String prefix) {
        return "Creates a monster!!!\n" +
                "Format: `" + prefix + "createmonster [monster ID] [monster name] [HP] [str] [mov] [def]`\n" +
                "Example: `" + prefix + "createmonster 1 dementor 200 5 7 9`";
    }
}
