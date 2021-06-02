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
import java.util.concurrent.TimeUnit;

public class StartPracticeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getId().equals("755975812909367387") && !ctx.getMember().getId().equals("268271834552598528") && !ctx.getMember().getId().equals("247907491436822528")) {
            ctx.getChannel().sendMessage("You have no permission to make a practice!\n" +
                    "If you think it is a mistake use the bug command! `" + prefix + "bug`").queue();
            return;
        }

        if (Data.raidMonster.containsKey(ctx.getChannel())) {
            ctx.getChannel().sendMessage("There is already a raid on going in this channel.").queue();
            return;
        }

        if (Data.practiceMonster.containsKey(ctx.getChannel())) {
            ctx.getChannel().sendMessage("There is already a practice on going in this channel.").queue();
            return;
        }

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("No id for the monster placed!!!\n\n" + getHelp(prefix)).queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().isEmpty()) {
            ctx.getChannel().sendMessage("No mentioned members placed!!!\n\n" + getHelp(prefix)).queue();
            return;
        }

        int monsterId = Integer.parseInt(ctx.getArgs().get(0));

        if (!Data.monster.containsKey(monsterId)) {
            ctx.getChannel().sendMessage("No such monster found!!!").queue();
            return;
        }

        Member member = ctx.getMessage().getMentionedMembers().get(0);

        Data.practiceMonster.put(ctx.getChannel(), monsterId);
        Data.practiceMember.put(ctx.getChannel(), member);

        ctx.getChannel().sendMessage("Creating...").complete().delete().queueAfter(2, TimeUnit.SECONDS);
        ctx.getChannel().sendMessage("Practice successfully created for the channel " + ctx.getChannel().getAsMention() + " for " + member.getAsMention() + "!!!").queue();

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Successfully created the practice!!!").setColor(Color.red);
        embedBuilder.addField("Monster ID:", String.valueOf(monsterId), false);
        embedBuilder.addField("Name:", Data.monster.get(monsterId).get(0), true);
        embedBuilder.addField("XP:", Data.monster.get(monsterId).get(1), false);
        embedBuilder.addField("Strength:", Data.monster.get(monsterId).get(2), false);
        embedBuilder.addField("Movement:", Data.monster.get(monsterId).get(3), false);
        embedBuilder.addField("Defense:", Data.monster.get(monsterId).get(4), false);
        embedBuilder.addField("For the user:", member.getAsMention(), false);

        embedBuilder.setTimestamp(LocalDateTime.now()).setFooter("Fight the monster now with " + prefix + "fight!!!");

        ctx.getChannel().sendMessage(embedBuilder.build()).complete().pin().queue();
    }

    @Override
    public String getName() {
        return "practice";
    }

    @Override
    public String getHelp(String prefix) {
        return "Starts a practice for a specific member!\n" +
                "Format: dc!practice [ID of monster you'd like to fight] [user to fight the monster]\n" +
                "Example: dc!startraid 1 @Wow\n" +
                "Response: Practice will start for @Wow against monster id 1!!!";
    }
}
