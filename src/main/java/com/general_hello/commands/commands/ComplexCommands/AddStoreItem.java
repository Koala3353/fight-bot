package com.general_hello.commands.commands.ComplexCommands;

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

public class AddStoreItem implements ICommand
{
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        try {
            if (ctx.getArgs().isEmpty()) {
                ctx.getChannel().sendMessage("No details for the shop placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(1).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the shop placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(2).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the shop placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(4).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the shop placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(5).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the shop placed!!!\n" + getHelp(prefix)).queue();
                return;
            }

            if (ctx.getArgs().get(3).isEmpty()) {
                ctx.getChannel().sendMessage("No details for the shop placed!!!\n" + getHelp(prefix)).queue();
                return;
            }
        } catch (Exception e) {
            ctx.getChannel().sendMessage("No details for the shop placed!!!\n" + getHelp(prefix)).queue();
            return;
        }

        String itemName = ctx.getArgs().get(0);
        String itemCost = ctx.getArgs().get(1);
        String imgUrl = ctx.getArgs().get(2);
        String strength = ctx.getArgs().get(3);
        String movement = ctx.getArgs().get(4);
        String defense = ctx.getArgs().get(5);


        ArrayList<String> info = new ArrayList<>();
        info.add(itemName);
        info.add(itemCost);
        info.add(imgUrl);
        info.add(strength);
        info.add(movement);
        info.add(defense);

        Data.storeItems.put(Data.itemCount, info);
        Data.itemCount ++;

        ctx.getChannel().sendMessage("Item successfully added!!!").queue();

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Successfully added the item!!!").setColor(Color.blue);
        embedBuilder.addField("Name:", itemName, true);
        embedBuilder.addField("Cost:", itemCost, false);
        embedBuilder.addField("IMG url:", imgUrl, false);
        embedBuilder.setThumbnail(imgUrl);
        embedBuilder.addField("Strength:", strength, false);
        embedBuilder.addField("Movement:", movement, false);
        embedBuilder.addField("Defense:", defense, false);

        embedBuilder.setTimestamp(LocalDateTime.now()).setFooter("Shop now with " + prefix + "itemshop !!!");

        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "additem";
    }

    @Override
    public String getHelp(String prefix) {
        return prefix + "additem [item name] [item cost] [img file (url link)] [strength] [movement] [defense]";
    }
}
