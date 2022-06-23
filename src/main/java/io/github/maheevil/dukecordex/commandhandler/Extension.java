package io.github.maheevil.dukecordex.commandhandler;

import io.github.maheevil.dukecordex.DukeCordEx;
import io.github.maheevil.dukecordex.commandhandler.annotations.NoArgs;
import io.github.maheevil.dukecordex.commandhandler.slashcommands.SlashCommandContext;
import io.github.maheevil.dukecordex.commandhandler.slashcommands.SlashCommandEx;
import io.github.maheevil.dukecordex.commandhandler.slashcommands.SlashCommandGroup;
import io.github.maheevil.dukecordex.commandhandler.slashcommands.SlashCommandRunner;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Extension {
    protected final  <T>ChatCommandEntry registerChatCommand(String name, Class<T> tClass, PermissionType[] permissionTypes, BiConsumer<T, MessageCreateEvent> consumer){
        if(!DukeCordEx.CommandMap.containsKey(name)) {
            DukeCordEx.CommandMap.put(name, new ChatCommandContainer<T>(
                    consumer, tClass,this,permissionTypes
            ));
            return new ChatCommandEntry(name,true);
        }else {
            return new ChatCommandEntry(name,false);
        }
    }

    protected final ChatCommandEntry registerChatCommand(String name, PermissionType[] permissionTypes, Consumer<MessageCreateEvent> consumer){
        if(!DukeCordEx.CommandMap.containsKey(name)) {
            DukeCordEx.CommandMap.put(name, new ChatCommandContainer<>(
                    consumer, NoArgs.class,this,permissionTypes
            ));
            return new ChatCommandEntry(name,true);
        }else {
            return new ChatCommandEntry(name,false);
        }
    }

    protected final  <T>ChatCommandEntry registerChatCommand(String name, Class<T> tClass, BiConsumer<T, MessageCreateEvent> consumer){
        if(!DukeCordEx.CommandMap.containsKey(name)) {
            DukeCordEx.CommandMap.put(name, new ChatCommandContainer<T>(
                    consumer, tClass,this,null
            ));
            return new ChatCommandEntry(name,true);
        }else {
            return new ChatCommandEntry(name,false);
        }
    }

    protected final ChatCommandEntry registerChatCommand(String name, Consumer<MessageCreateEvent> consumer){
        if(!DukeCordEx.CommandMap.containsKey(name)) {
            DukeCordEx.CommandMap.put(name, new ChatCommandContainer<>(
                    consumer, NoArgs.class,this,null
            ));
            return new ChatCommandEntry(name,true);
        }else {
            return new ChatCommandEntry(name,false);
        }
    }

    protected final<T> Object registerBasicSlashCommand(
            String baseName, String description, Class<T> argClass, BiConsumer<T,SlashCommandContext> consumer
    ){
        var bruh = new SlashCommandEx(baseName,description);
        bruh.addBaseBrancingRunner("main",argClass,consumer);
        DukeCordEx.SlashCommandMap.put(baseName,bruh);
        return null;
    }
    @SafeVarargs
    protected final<T> Object registerBasicSlashCommand(String baseName, String description, SlashCommandRunner<T>... slashCommandRunners){
        var bruh = new SlashCommandEx(baseName,description);
        Arrays.stream(slashCommandRunners).forEach(
                tSlashCommandRunner -> bruh.baseBranchingCommands.put(tSlashCommandRunner.name,tSlashCommandRunner)
        );
        DukeCordEx.SlashCommandMap.put(baseName,bruh);
        return null;
    }

    protected final Object registerGroupedSlashCommand(String baseName, String description, SlashCommandGroup... slashCommandGroups){
        var bruhe = new SlashCommandEx(baseName,description);
        Arrays.stream(slashCommandGroups).forEach(
                slashCommandGroup -> bruhe.slashCommandGroups.put(slashCommandGroup.name, slashCommandGroup)
        );
        DukeCordEx.SlashCommandMap.put(baseName,bruhe);
        return null;
    }
}
