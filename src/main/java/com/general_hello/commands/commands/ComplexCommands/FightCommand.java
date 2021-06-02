package com.general_hello.commands.commands.ComplexCommands;

import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.Storing.Data;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class FightCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        if (!Data.raidMonster.containsKey(ctx.getChannel()) && !Data.practiceMember.containsKey(ctx.getChannel())) {
            ctx.getChannel().sendMessage("No raids is currently on going in this channel.").queue();
            return;
        }

        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (!Data.practiceMember.containsKey(ctx.getChannel()) && !Data.raidMembers.containsKey(ctx.getChannel())) {
            try {
                if (!Data.raidMembers.get(ctx.getChannel()).contains(ctx.getMember())) {
                    ctx.getChannel().sendMessage("You have not yet joined the raid / there is no practice on going!\n" +
                            "Join now by typing `" + prefix + "joinraid`").queue();
                    return;
                }
            } catch (Exception e) {
                ctx.getChannel().sendMessage("You have not yet joined the raid / there is no practice on going!\n" +
                        "Join now by typing `" + prefix + "joinraid`").queue();
                return;
            }
        }

        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852395637047306")) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("798429801748234280")) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById("833852292825743370")) && !ctx.getMember().getId().equals("755975812909367387") && !ctx.getMember().getId().equals("268271834552598528")) {
            ctx.getChannel().sendMessage("You don't have the permission to fight in this raid/practice!!!").queue();
            return;
        }

        if (!Data.raidHits.containsKey(ctx.getMember())) {
            Data.raidHits.put(ctx.getMember(), 0);
        }

        Data.statName.put(2, "strength");
        Data.statName.put(3, "movement");
        Data.statName.put(4, "defense");

        ArrayList<Integer> stats = Data.user.get(ctx.getMember().getId());
        int level = stats.get(0);
        int HP = stats.get(1);
        int strength = stats.get(2);
        int movement = stats.get(3);
        int defense = stats.get(4);

        Integer monsterid = Data.raidMonster.get(ctx.getChannel());

        if (Data.practiceMember.containsKey(ctx.getChannel())) {
            if (Data.practiceMember.get(ctx.getChannel()).getId().equals(ctx.getMember().getId())) {
                ArrayList<String> monster = Data.monster.get(Data.practiceMonster.get(ctx.getChannel()));

                int strengthMonster = Integer.parseInt(monster.get(2));
                int HPMonster = Integer.parseInt(monster.get(1));
                int movementMonster = Integer.parseInt(monster.get(3));
                int defenseMonster = Integer.parseInt(monster.get(4));
                int levelMonster = (HPMonster - 5) / 2;

                if (!Data.lastFight.containsKey(ctx.getMember())) {
                    Data.lastFight.put(ctx.getMember(), LocalDateTime.now().getDayOfMonth());
                } else {
                    if ((Data.lastFight.get(ctx.getMember()) + 1) > LocalDateTime.now().getDayOfMonth()) {
                        ctx.getChannel().sendMessage("You cannot fight until it is the next day.").queue();
                        return;
                    } else {
                        Data.lastFight.put(ctx.getMember(), LocalDateTime.now().getDayOfMonth());
                    }
                }

                //Practice code
                if ((((((((2 * level + 2) * movement * strength) / defenseMonster) / 50) + 2) * randomNumber(217, 300)) / 255) > HPMonster) {
                    int lol = randomNumber(2, 4);
                    Integer s = stats.get(lol);
                    stats.set(lol, s + 1);
                    Data.user.put(ctx.getMember().getId(), stats);

                    if (Data.memberWins.isEmpty()) {
                        Data.memberWins.put(ctx.getMember(), 1);
                    } else {
                        Integer wins = Data.memberWins.get(ctx.getMember());
                        Data.memberWins.put(ctx.getMember(), wins + 1);
                    }

                    Data.practiceMember.remove(ctx.getChannel());
                    Data.practiceMonster.remove(ctx.getChannel());
                    ctx.getChannel().sendMessage("Congratulations you have won against the " + monster.get(0)  + " and got a `+1` in " + Data.statName.get(lol) + " as a reward!").queue();
                    return;
                }

                if (((((((((3 * levelMonster + 2) * movementMonster * strengthMonster) / defense) / 50) + 2) * randomNumber(217, 300)) / 200) / 10) > HP) {
                    int lol = randomNumber(2, 4);
                    Integer s = stats.get(lol);
                    stats.set(lol, s + 1);
                    Data.user.put(ctx.getMember().getId(), stats);

                    if (Data.memberWins.isEmpty()) {
                        Data.memberWins.put(ctx.getMember(), 1);
                    } else {
                        Integer wins = Data.memberWins.get(ctx.getMember());
                        Data.memberWins.put(ctx.getMember(), wins + 1);
                    }

                    Data.practiceMember.remove(ctx.getChannel());
                    Data.practiceMonster.remove(ctx.getChannel());

                    ctx.getChannel().sendMessage("Congratulations you have won against the " + monster.get(0)  + " and got a `+1` in " + Data.statName.get(lol) + " as a reward!").queue();
                } else {
                    Data.practiceMember.remove(ctx.getChannel());
                    Data.practiceMonster.remove(ctx.getChannel());

                    ctx.getChannel().sendMessage("The monster has defeated you try again next time :|").queue();
                }
                if (Data.memberLoss.isEmpty()) {
                    Data.memberLoss.put(ctx.getMember(), 1);
                } else {
                    Integer wins = Data.memberLoss.get(ctx.getMember());
                    Data.memberLoss.put(ctx.getMember(), wins + 1);
                }
            }
        } else {
            if (!Data.lastFightRaid.containsKey(ctx.getMember())) {
                Data.lastFightRaid.put(ctx.getMember(), LocalDateTime.now().getDayOfMonth());
            } else {
                if ((Data.lastFightRaid.get(ctx.getMember()) + 1) > LocalDateTime.now().getDayOfMonth()) {
                    ctx.getChannel().sendMessage("You cannot fight until it is the next day.").queue();
                    return;
                } else {
                    Data.lastFightRaid.put(ctx.getMember(), LocalDateTime.now().getDayOfMonth());
                }
            }

            ArrayList<String> monster = Data.monster.get(Data.raidMonster.get(ctx.getChannel()));

            int HPMonster = Integer.parseInt(monster.get(1));
            int levelMonster = (HPMonster - 5) / 2;

            //Raid code

            int attack = (5 * strength - randomNumber(1, strength)) + (5 * defense - (randomNumber(1, defense)) + (5 * movement - randomNumber(1, strength)));
            HPMonster = HPMonster - attack;
            Data.monster.get(Data.raidMonster.get(ctx.getChannel())).set(1, String.valueOf(HPMonster));
            if (Integer.parseInt(Data.monster.get(Data.raidMonster.get(ctx.getChannel())).get(1)) <= 0) {
                int lol = randomNumber(2, 4);
                Integer s = stats.get(lol);
                stats.set(lol, s + 1);
                Data.user.put(ctx.getMember().getId(), stats);

                if (Data.raidMemberWins.isEmpty()) {
                    Data.raidMemberWins.put(ctx.getMember(), 1);
                } else {
                    Integer wins = Data.raidMemberWins.get(ctx.getMember());
                    Data.raidMemberWins.put(ctx.getMember(), wins + 1);
                }
                ctx.getChannel().sendMessage("Congratulations you have won against the " + monster.get(0)  + " and got a `+1` in " + Data.statName.get(lol) + " as a reward!").queue();

                ctx.getChannel().sendMessage("The raid has already ended!").queue();
                Data.raidMembers.remove(ctx.getChannel());
                Data.raidMonster.remove(ctx.getChannel());
                Data.raidTime.remove(ctx.getChannel());
                Data.monster.remove(monsterid);
                ctx.getChannel().sendMessage("Removed the monster with the ID of `" + monsterid + "`!!!").queue();
            } else {
                ctx.getChannel().sendMessage("Congratulations you dealt a total of `" + attack + "` against the monster! The monster now has only " + HPMonster + " left.").queue();
                Integer wins = Data.raidHits.get(ctx.getMember());
                Data.raidMemberWins.put(ctx.getMember(), wins + 1);
            }
        }
    }

    public int randomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    @Override
    public String getName() {
        return "fight";
    }

    @Override
    public String getHelp(String prefix) {
        return "Fight against the monster chosen to get rewards\n" +
                "Usage: `" + prefix + "fight`";
    }
}
