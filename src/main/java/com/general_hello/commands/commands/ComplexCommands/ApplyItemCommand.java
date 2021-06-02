package com.general_hello.commands.commands.ComplexCommands;

import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.Storing.Data;
import net.dv8tion.jda.api.entities.Member;

import java.io.IOException;
import java.util.ArrayList;

public class ApplyItemCommand implements ICommand
{
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getId().equals("247907491436822528") && !ctx.getMember().getId().equals("268271834552598528") && !ctx.getMember().getId().equals("755975812909367387")) {
            ctx.getChannel().sendMessage("You have no permission to apply an item!\n" +
                    "If you think it is a mistake use the bug command! `" + prefix + "bug`").queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().isEmpty()) {
            ctx.getChannel().sendMessage("You didn't mention a user to apply the item to\n" +
                    getHelp(prefix)).queue();
            return;
        }

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("You didn't mention what item to apply to the user!\n" +
                    getHelp(prefix)).queue();
            return;
        }

        int itemNumber = Integer.parseInt(ctx.getArgs().get(0));
        Member member = ctx.getMessage().getMentionedMembers().get(0);

        if (!Data.storeItems.containsKey(itemNumber)) {
            ctx.getChannel().sendMessage("No such item found.").queue();
            return;
        }

        if (!Data.user.containsKey(member.getId())) {
            ctx.getChannel().sendMessage("No such user found in the database!\n" +
                    "Use `" + prefix + "adduser` to add the user").queue();
            return;
        }

        ArrayList<Integer> userStats = Data.user.get(member.getId());

        Integer strength = userStats.get(2);
        Integer movement = userStats.get(3);
        Integer defense = userStats.get(4);

        ArrayList<String> itemInfo = Data.storeItems.get(itemNumber);
        Integer strengthAdd = Integer.valueOf(itemInfo.get(3));
        Integer movementAdd = Integer.valueOf(itemInfo.get(4));
        Integer defenseAdd = Integer.valueOf(itemInfo.get(5));


        userStats.set(2, strength + strengthAdd);
        userStats.set(3, movement + movementAdd);
        userStats.set(4, defense + defenseAdd);

        ctx.getChannel().sendMessage("Successfully applied the item!!").queue();
    }

    @Override
    public String getName() {
        return "apply";
    }

    @Override
    public String getHelp(String prefix) {
        return "Applies the shop item to the user\n" +
                "Usage: `" + prefix + "apply [item number] [mention user]`\n" +
                "Example `" + prefix + "apply 1 @Wow`";
    }
}
