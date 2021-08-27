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
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;

public class CommandAdd {
	
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		dispatcher.register(Commands.literal("jobs-add").requires((source)-> source.hasPermission(2))
			.then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("job", JobArgument.job())
			.then(Commands.argument("xp", LongArgumentType.longArg(0, Constants.MAX_XP)).executes((ctx) ->
				{
					addXP(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), JobArgument.getJob(ctx, "job"), LongArgumentType.getLong(ctx, "xp"));
					return 0;
				}))))
			);
	}

	
	private static void addXP(CommandSource source, ServerPlayerEntity target, Job job, long xp)
	{
		PlayerData.getPlayerJobs(target).gainXP(job, xp, target);
        PacketHandler.INSTANCE.sendTo(new PacketUpdateClientJob(PlayerData.getPlayerJobs(target).toTotalXPs()), target.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        
        if(source.getEntity() instanceof ServerPlayerEntity)
        {
        	ServerPlayerEntity sender = (ServerPlayerEntity)source.getEntity();
        	PacketHandler.INSTANCE.sendTo(new PacketSendChatMessage(new StringTextComponent(xp + " xp added to " + target.getName().getString() + " for job " + job.name())), 
    				sender.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
	}
}
