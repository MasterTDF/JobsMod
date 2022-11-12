package com.mastertdf.jobs.commands;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mastertdf.jobs.util.Constants.Job;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

import java.util.concurrent.CompletableFuture;

public class JobArgument implements ArgumentType<Job> {

    public static JobArgument job() {
        return new JobArgument();
    }

    public static Job getJob(CommandContext<CommandSource> ctx, String name) {
        return ctx.getArgument(name, Job.class);
    }

    @Override
    public Job parse(StringReader reader) throws CommandSyntaxException {
        return Job.fromString(reader.readString());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return builder.suggest("HUNTER").suggest("WIZARD").suggest("FARMER").suggest("MINER").suggest("ENGINEER").suggest("SMITH").suggest("ARTISAN").buildFuture();
    }


    public static class Serializer implements IArgumentSerializer<JobArgument> {

        @Override
        public void serializeToNetwork(JobArgument argument, PacketBuffer buf) {
		}

        @Override
        public JobArgument deserializeFromNetwork(PacketBuffer buf) {
            return JobArgument.job();
        }

        @Override
        public void serializeToJson(JobArgument argument, JsonObject json) {
		}

    }

}
