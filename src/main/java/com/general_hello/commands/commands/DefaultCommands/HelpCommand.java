package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.CommandManager;
import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852292825743370")) && !ctx.getMember().getId().equals("755975812909367387")) {
            ctx.getChannel().sendMessage("You don't have the `permission` to do this command!!!").queue();
            return;
        }

        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        //Summary in groups
        String group = "Bot\n" +
                "type `" + prefix + "help bot` to find out what are the commands\n\n";
        String group1 = "Mod\n" +
                "type `" + prefix + "help mod` to find out what are the commands\n\n";
        String group2 = "Owner\n" +
                "type `" + prefix + "help owner` to find out what are the commands\n\n";

        if (args.isEmpty()) {
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setTitle("Help!!!");
            embedBuilder.setColor(Color.cyan);
            embedBuilder.addField(group,"", false);

            if (ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) || ctx.getMember().getId().equals("755975812909367387")) {
                embedBuilder.addField(group1, "", false);
            }

            if (ctx.getAuthor().getId().equals(Config.get("owner_id")) || ctx.getAuthor().getId().equals("755975812909367387")) {
                embedBuilder.addField(group2, "", false);
            }

            embedBuilder.setFooter("\nType " + prefix + "help [group name] to see their commands");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        if (args.get(0).equals("mod")) {
            if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("268271834552598528")) && !ctx.getMember().getId().equals("755975812909367387")) {
                ctx.getChannel().sendMessage("No permission to view this command!").queue();
                return;
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Mod Commands");
            embedBuilder.setColor(Color.green);
            embedBuilder.addField("1.) Set Prefix Command","`" + prefix + "setprefix`", false);
            embedBuilder.addField("2.) Add User Command", "`" + prefix + "adduser`", false);
            embedBuilder.addField("3.) Create Monster Command", "`" + prefix + "createmonster`", false);
            embedBuilder.addField("4.) Delete User Command", "`" + prefix + "deleteuser`", false);
            embedBuilder.addField("5.) Monster List Command", "`" + prefix + "monsterlist`", false);
            embedBuilder.addField("6.) Start Raid Command", "`" + prefix + "startraid`", false);
            embedBuilder.addField("7.) Start Practice Command", "`" + prefix + "practice`", false);
            embedBuilder.addField("8.) Apply Shop Item Command", "`" + prefix + "apply`", false);
            embedBuilder.addField("9.) Add Item Command", "`" + prefix + "additem`", false);



            embedBuilder.setFooter("\nType " + prefix + "help [command name] to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        if (args.get(0).equals("owner")) {

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Owner Commands");
            embedBuilder.setColor(Color.green);
            embedBuilder.addField("1.) Shutdown Command","`" + prefix + "shutdown`", false);


            embedBuilder.setFooter("\nType " + prefix + "help [command name] to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        if (args.get(0).equals("bot")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("About the Bot Commands");
            embedBuilder.setColor(Color.blue);
            embedBuilder.addField("1.) View Profile Command", "`" + prefix + "profile`", false);
            embedBuilder.addField("2.) Join Raid Command", "`" + prefix + "joinraid`", false);
            embedBuilder.addField("2.) Join Practice Command", "`" + prefix + "joinpractice`", false);
            embedBuilder.addField("3.) Fight Raid/Practice Command", "`" + prefix + "fight`", false);
            embedBuilder.addField("4.) Shop Command", "`" + prefix + "itemshop`", false);
            embedBuilder.addField("5.) Bug Command","`" + prefix + "bug`", false);
            embedBuilder.addField("6.) Ping Command", "`" + prefix + "ping`", false);


            embedBuilder.setFooter("\nType " + prefix + "help [command name] to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        }

        embedBuilder.setTitle("Help!!!");
        embedBuilder.setColor(Color.cyan);
        embedBuilder.addField(command.getHelp(prefix), "Try it now!", false);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        embedBuilder.setFooter("Time: " + dtf.format(now));
        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows the list with commands in the bot\n" +
                "Usage: `" + prefix + "help [command]`";
    }

    @Override
    public List<String> getAliases() {
        List<String> strings = new java.util.ArrayList<>();
        strings.add("commands");
        strings.add("cmds");
        strings.add("commandlist");
        return strings;
    }
}
