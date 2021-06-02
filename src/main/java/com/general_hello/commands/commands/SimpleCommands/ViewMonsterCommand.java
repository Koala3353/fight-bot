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

public class ViewMonsterCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getId().equals("247907491436822528") && !ctx.getMember().getId().equals("268271834552598528") && !ctx.getMember().getId().equals("755975812909367387")) {
            ctx.getChannel().sendMessage("You dont have the permission to do this command!!!").queue();
            return;
        }

        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (Data.monster.isEmpty()) {
            ctx.getChannel().sendMessage("No `monsters` made yet!!\n" +
                    "Create one now by typing `" + prefix + "createmonster`").queue();
            return;
        }

        int x = 0;

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("List of Monsters").setColor(Color.green);

        while (Data.monster.size() > x) {
            ArrayList<String> monster = Data.monster.get(Data.monsterId.get(x));
            embedBuilder.addField("Monster ID:", String.valueOf(Data.monsterId.get(x)), true);
            embedBuilder.addField("Name:", monster.get(0), true);
            embedBuilder.addField("HP:", monster.get(1), true);
            embedBuilder.addField("Strength:", monster.get(2), true);
            embedBuilder.addField("Movement:", monster.get(3), true);
            embedBuilder.addField("Defense:", monster.get(4), true);
            embedBuilder.addBlankField(false);

            x ++;
        }
        embedBuilder.setTimestamp(LocalDateTime.now()).setFooter("See available commands with " + prefix + "help!!!");
        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "monsterlist";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows the list of monsters\n" +
                "Usage: `" + prefix + "monsterlist`";
    }
}
