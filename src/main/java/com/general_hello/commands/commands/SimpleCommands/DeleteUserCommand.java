package com.general_hello.commands.commands.SimpleCommands;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Storing.Data;
import net.dv8tion.jda.api.entities.Member;

import java.io.IOException;

public class DeleteUserCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        if (ctx.getMessage().getMentionedMembers().isEmpty()) {
            ctx.getChannel().sendMessage("You didn't ping the user to delete").queue();
            return;
        }
        Member member = ctx.getMessage().getMentionedMembers().get(0);

        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getId().equals("247907491436822528") && !ctx.getMember().getId().equals("268271834552598528") && !ctx.getMember().getId().equals("755975812909367387")) {
            ctx.getChannel().sendMessage("You dont have the permission to do this command!!!").queue();
            return;
        }

        if (!Data.user.containsKey(member.getId())) {
            ctx.getChannel().sendMessage("User not found in the database!!!").queue();
            return;
        }

        try {
            Data.user.remove(member.getId());
        } catch (Exception e) {
            ctx.getChannel().sendMessage("User not found!!!").queue();
            return;
        }

        ctx.getChannel().sendMessage("Successfully deleted " + member.getAsMention() + " from the database!!!").queue();
    }

    @Override
    public String getName() {
        return "deleteuser";
    }

    @Override
    public String getHelp(String prefix) {
        return "Deletes the user from the database\n" +
                "`Warning user cannot be restored after deleting`\n" +
                "\n" +
                "Format: " + prefix + "deleteuser [user]\n" +
                "Example: " + prefix + "deleteuser @Zoey";
    }
}
