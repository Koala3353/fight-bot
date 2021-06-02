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

public class ItemShopCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (Data.storeItems.isEmpty()) {
            ctx.getChannel().sendMessage("No `items` made yet!!\n" +
                    "Create one now by typing `" + prefix + "additem`").queue();
            return;
        }

        int x = 0;

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("List of Items").setColor(Color.yellow);

        while (Data.storeItems.size() > x) {
            ArrayList<String> items = Data.storeItems.get((x + 1));

            embedBuilder.addField("Item number: ", String.valueOf((x + 1)), true);
            embedBuilder.addField("Name:", items.get(0), true);
            embedBuilder.addField("Cost:", items.get(1), true);
            embedBuilder.addField("Strength:", items.get(3), true);
            embedBuilder.addField("Movement:", items.get(4), true);
            embedBuilder.addField("Defense:", items.get(5), true);
            embedBuilder.setThumbnail(items.get(2));
            embedBuilder.setTimestamp(LocalDateTime.now()).setFooter("Shop now with " + prefix + "itemshop !!!");

            ctx.getChannel().sendMessage(embedBuilder.build()).queue();
            embedBuilder.clearFields();

            x ++;
        }

    }

    @Override
    public String getName() {
        return "itemshop";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows the list of items\n" +
                "Usage: `" + prefix + "itemshop`";
    }
}
