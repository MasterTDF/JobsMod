package com.polarfox27.jobs.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.polarfox27.jobs.data.PlayerData;
import com.polarfox27.jobs.network.PacketSendChatMessage;
import com.polarfox27.jobs.network.PacketUpdateClientJob;
import com.polarfox27.jobs.util.Constants;
import com.polarfox27.jobs.util.Constants.Job;
import com.polarfox27.jobs.util.handler.PacketHandler;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.impl.EffectCommand;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;

public class CommandSet {
	
	
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		dispatcher.register(Commands.literal("jobs-set")
			.requires((source) -> {return source.hasPermission(2);})
			.then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("job", JobArgument.job())
			.then(Commands.argument("total xp", LongArgumentType.longArg(0, Constants.MAX_XP)).executes((ctx) ->
				{
					setJobs(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), JobArgument.getJob(ctx, "job"), LongArgumentType.getLong(ctx, "total xp"));
					return 0;
				}))))
			.then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("job", JobArgument.job())
					.then(Commands.argument("level", IntegerArgumentType.integer(0, 25)).then(Commands.argument("xp", LongArgumentType.longArg(0, Constants.MAX_XP)).executes((ctx) ->
						{
							setJobs(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), JobArgument.getJob(ctx, "job"), IntegerArgumentType.getInteger(ctx, "level"), LongArgumentType.getLong(ctx, "xp"));
							return 0;
						})))))
			);
	}
	
	private static void setJobs(CommandSource source, ServerPlayerEntity target, Job job, int lvl, long xp)
	{
		setJobs(source, target, job, Constants.TOTAL_XP_BY_LEVEL[lvl] + xp);
	}
	
	private static void setJobs(CommandSource source, ServerPlayerEntity target, Job job, long total)
	{
		PlayerData.getPlayerJobs(target).set(job, total);
        PacketHandler.INSTANCE.sendTo(new PacketUpdateClientJob(PlayerData.getPlayerJobs(target).toTotalXPs()), target.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        
        if(source.getEntity() instanceof ServerPlayerEntity)
        {
        	ServerPlayerEntity sender = (ServerPlayerEntity)source.getEntity();
        	int lvl = PlayerData.getPlayerJobs(target).getLevelByJob(job);
        	long xp = PlayerData.getPlayerJobs(target).getXPByJob(job);
        	PacketHandler.INSTANCE.sendTo(new PacketSendChatMessage(new StringTextComponent("Job " + job.name() + " of " + target.getDisplayName().getString() + " set to lvl " + lvl + ", xp " + xp)), 
        			sender.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
	}

}
