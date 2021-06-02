package com.general_hello.commands.commands.ComplexCommands;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Storing.Data;
import net.dv8tion.jda.api.entities.Member;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JoinRaidCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("798429801748234280")) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852292825743370")) && !ctx.getMember().getId().equals("755975812909367387") && !ctx.getMember().getId().equals("268271834552598528")) {
            ctx.getChannel().sendMessage("You don't have the permission to join this raid!!!").queue();
            return;
        }

        if (!Data.raidMonster.containsKey(ctx.getChannel())) {
            ctx.getChannel().sendMessage("No raids is currently on going in this channel :|").queue();
            return;
        }

        if (LocalDateTime.now().isAfter(Data.raidTime.get(ctx.getChannel()))) {
            ctx.getChannel().sendMessage("The raid has already ended and we failed to defeat the monster :( !").queue();
            Data.raidMembers.remove(ctx.getChannel());
            Data.raidMonster.remove(ctx.getChannel());
            Data.raidTime.remove(ctx.getChannel());
            return;
        }

        ArrayList<Member> members = new ArrayList<>();

        if (!Data.raidMembers.containsKey(ctx.getChannel())) {
            Data.raidMembers.put(ctx.getChannel(), members);
        }

        if (Data.raidMembers.get(ctx.getChannel()).contains(ctx.getMember())) {
            ctx.getChannel().sendMessage(ctx.getMember().getAsMention() + " already joined the raid!").queue();
            return;
        }

        members = Data.raidMembers.get(ctx.getChannel());
        members.add(ctx.getMember());

        Data.raidMembers.put(ctx.getChannel(), members);
        ctx.getChannel().sendMessage("Successfully added " + ctx.getMember().getAsMention() + " to the raid!").queue();
    }

    @Override
    public String getName() {
        return "joinraid";
    }

    @Override
    public String getHelp(String prefix) {
        return "Lets the user join the raid on-going in the text channel\n" +
                "Usage: `" + prefix + "joinraid`";
    }
}
