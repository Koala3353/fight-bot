package com.general_hello.commands.commands.Storing;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    public static HashMap<Integer, ArrayList<String>> monster = new HashMap<>();
    public static HashMap<String, ArrayList<Integer>> user = new HashMap<>();
    public static HashMap<Integer, ArrayList<String>> storeItems = new HashMap<>();
    public static ArrayList<Integer> monsterId = new ArrayList<>();
    public static HashMap<TextChannel, LocalDateTime> raidTime = new HashMap<>();
    public static HashMap<TextChannel, Integer> raidMonster = new HashMap<>();
    public static HashMap<TextChannel, ArrayList<Member>> raidMembers = new HashMap<>();
    public static HashMap<TextChannel, Integer> practiceMonster = new HashMap<>();
    public static HashMap<TextChannel, Member> practiceMember = new HashMap<>();
    public static HashMap<Member, Integer> memberWins = new HashMap<>();
    public static HashMap<Member, Integer> raidMemberWins = new HashMap<>();
    public static HashMap<Member, Integer> raidMemberLoss = new HashMap<>();
    public static HashMap<Member, Integer> memberLoss = new HashMap<>();
    public static HashMap<Member, Integer> raidHits = new HashMap<>();
    public static HashMap<Member, Integer> lastFight = new HashMap<>();
    public static HashMap<Member, Integer> lastFightRaid = new HashMap<>();
    public static Integer itemCount = 1;
    public static HashMap<Integer, String> statName = new HashMap<>();
}
