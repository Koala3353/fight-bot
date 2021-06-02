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
import java.util.concurrent.TimeUnit;

public class StartRaidCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getId().equals("247907491436822528") && !ctx.getMember().getId().equals("268271834552598528") && !ctx.getMember().getId().equals("755975812909367387")) {
            ctx.getChannel().sendMessage("You have no permission to make a raid!\n" +
                    "If you think it is a mistake use the bug command! `" + prefix + "bug`").queue();
            return;
        }

        if (Data.practiceMonster.containsKey(ctx.getChannel())) {
            ctx.getChannel().sendMessage("There is already a practice on going in this channel.").queue();
            return;
        }

        if (Data.raidMonster.containsKey(ctx.getChannel())) {
            ctx.getChannel().sendMessage("There is already a raid on going in this channel.").queue();
            return;
        }

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("No id for the monster placed!!!\n\n" + getHelp(prefix)).queue();
            return;
        }

        if (ctx.getArgs().get(1).isEmpty()) {
            ctx.getChannel().sendMessage("No time for the raid placed!!!\n\n" + getHelp(prefix)).queue();
            return;
        }

        int monsterId = Integer.parseInt(ctx.getArgs().get(0));

        if (!Data.monster.containsKey(monsterId)) {
            ctx.getChannel().sendMessage("No such monster found!!!").queue();
            return;
        }

        String time = ctx.getArgs().get(1);
        String[] timeSplit = time.split(",");

        String days;
        String hours;
        String minutes;

        try {
            days = timeSplit[0];
            hours = timeSplit[1];
            minutes = timeSplit[2];
        } catch (Exception e) {
            ctx.getChannel().sendMessage("Check the format of the time!").queue();
            return;
        }

        LocalDateTime timeToStop = LocalDateTime.now().plusDays(Long.parseLong(days)).plusHours(Long.parseLong(hours)).plusMinutes(Long.parseLong(minutes));

        Data.raidMonster.put(ctx.getChannel(), monsterId);
        Data.raidTime.put(ctx.getChannel(), timeToStop);

        ctx.getChannel().sendMessage("Creating...").complete().delete().queueAfter(2, TimeUnit.SECONDS);
        ctx.getChannel().sendMessage("Raid successfully created for the channel " + ctx.getChannel().getAsMention() + "!!!").queue();

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Successfully created the raid!!!").setColor(Color.red);
        embedBuilder.addField("Monster ID:", String.valueOf(monsterId), false);
        embedBuilder.addField("Name:", Data.monster.get(monsterId).get(0), true);
        embedBuilder.addField("XP:", Data.monster.get(monsterId).get(1), false);
        embedBuilder.addField("Strength:", Data.monster.get(monsterId).get(2), false);
        embedBuilder.addField("Movement:", Data.monster.get(monsterId).get(3), false);
        embedBuilder.addField("Defense:", Data.monster.get(monsterId).get(4), false);
        embedBuilder.addField("Time Length:", days + " days " + hours + " hours " + minutes + " minutes", false);
        if (timeToStop.getMinute() < 10) {
            embedBuilder.addField("Ends in:", timeToStop.getMonth().name() + " " + timeToStop.getDayOfMonth() + ", " + timeToStop.getYear() + ". On " + timeToStop.getHour() + ":0" + timeToStop.getMinute(), false);
        } else {
            embedBuilder.addField("Ends in:", timeToStop.getMonth().name() + " " + timeToStop.getDayOfMonth() + ", " + timeToStop.getYear() + ". On " + timeToStop.getHour() + ":" + timeToStop.getMinute(), false);
        }

        embedBuilder.setTimestamp(LocalDateTime.now()).setFooter("Join the raid with " + prefix + "joinraid!!!");

        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "startraid";
    }

    @Override
    public String getHelp(String prefix) {
        return "Starts a raid!\n" +
                "Format: `dc!startraid [ID of monster you'd like to fight] [time duration of the raid in d/h/m format]`\n" +
                "Example: `dc!startraid 1 7,6,0`\n" +
                "Response: Raid will last for 7 days 6 hours 0 minutes against monster id 1!!!";
    }
}
