package com.polarfox27.jobs.commands;

import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.polarfox27.jobs.util.Constants.Job;

import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

public class JobArgument implements ArgumentType<Job>{

	@Override
	public Job parse(StringReader reader) throws CommandSyntaxException 
	{
		return Job.fromString(reader.readString());
	}
	
	public static JobArgument job()
	{
		return new JobArgument();
	}
	
	public static Job getJob(CommandContext<CommandSource> ctx, String name)
	{
		return ctx.getArgument(name, Job.class);
	}
	
	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) 
	{
		return builder.suggest("HUNTER").suggest("MAGICIAN").suggest("FARMER").suggest("MINER").buildFuture();
	}
	
	
	public static class Serializer implements IArgumentSerializer<JobArgument>{

		@Override
		public void serializeToNetwork(JobArgument argument, PacketBuffer buf) 
		{
			;			
		}

		@Override
		public JobArgument deserializeFromNetwork(PacketBuffer buf) 
		{
			return JobArgument.job();
		}

		@Override
		public void serializeToJson(JobArgument argument, JsonObject json) 
		{
			;
		}
		
	}

}
