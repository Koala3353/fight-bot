package com.general_hello.commands.commands.ComplexCommands;

import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.Storing.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserProfileCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        Member member;

        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("798429801748234280")) && !ctx.getMember().getId().equals("247907491436822528") && !ctx.getMember().getId().equals("268271834552598528") && !ctx.getMember().getId().equals("247907491436822528") && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852292825743370")) && !ctx.getMember().getId().equals("755975812909367387")) {
            ctx.getChannel().sendMessage("You don't have the permission to view this users profile!!!").queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().isEmpty()) {
            member = ctx.getMember();
        } else {
            member = ctx.getMessage().getMentionedMembers().get(0);
        }

        if (!Data.user.containsKey(member.getId())) {
            ctx.getChannel().sendMessage("The user you mention is not found in the database").queue();
            return;
        }

        if (!Data.memberWins.containsKey(member)) {
            Data.memberWins.put(member, 0);
        }

        if (!Data.memberLoss.containsKey(member)) {
            Data.memberLoss.put(member, 0);
        }

        if (!Data.raidMemberWins.containsKey(member)) {
            Data.raidMemberWins.put(member, 0);
        }

        if (!Data.raidMemberLoss.containsKey(member)) {
            Data.raidMemberLoss.put(member, 0);
        }

        if (!Data.raidHits.containsKey(member)) {
            Data.raidHits.put(member, 0);
        }

        ArrayList<Integer> memberInfo = Data.user.get(member.getId());

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle(member.getEffectiveName() + "'s Profile").setColor(Color.cyan);
        embedBuilder.addField("User ID:", member.getId(), false);
        embedBuilder.addField("User Name:", member.getUser().getAsTag(), false);
        embedBuilder.addField("Level:", String.valueOf(memberInfo.get(0)), true);
        embedBuilder.addField("Strength:", String.valueOf(memberInfo.get(2)), false);
        embedBuilder.addField("Movement:", String.valueOf(memberInfo.get(3)), false);
        embedBuilder.addField("Defense:", String.valueOf(memberInfo.get(4)), false);
        embedBuilder.addField("Current Max HP:", String.valueOf(memberInfo.get(1)), false);
        embedBuilder.addField("Battle raids/practice won:", Data.memberWins.get(member).toString(), false);
        embedBuilder.addField("Battle raids/practice lost:", Data.memberLoss.get(member).toString(), false);
        embedBuilder.addField("Total Hits for practice:", String.valueOf((Data.memberLoss.get(member) + Data.memberWins.get(member))), false);
        embedBuilder.addField("Total Hits for raids:", String.valueOf((Data.raidMemberLoss.get(member) + Data.raidMemberWins.get(member)) + Data.raidHits.get(member)), false);


        embedBuilder.setTimestamp(LocalDateTime.now()).setFooter("Fight the monster now with " + prefix + "fight!!!");

        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "profile";
    }

    @Override
    public String getHelp(String prefix) {
        return "Format: `" + prefix + "profile` OR `" + prefix + "profile [mention user]`\n" +
                "End result: [username] [level] [str][mov][def] [current max HP] [battles/raids won/lost]";
    }
}
