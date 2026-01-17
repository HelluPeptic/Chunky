package org.popcraft.chunky.command;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.TranslationKey;

public class SpeedCommand implements ChunkyCommand {
    private final Chunky chunky;

    public SpeedCommand(final Chunky chunky) {
        this.chunky = chunky;
    }

    @Override
    public void execute(final Sender sender, final CommandArguments arguments) {
        if (arguments.size() == 0) {
            // Show current speed settings
            final int currentMaxWorking = chunky.getConfig().getMaxConcurrentChunks();
            final int currentDelay = chunky.getConfig().getChunkGenerationDelay();
            sender.sendMessagePrefixed(TranslationKey.FORMAT_SPEED_STATUS, currentMaxWorking, currentDelay);
            return;
        }

        if (arguments.size() < 2) {
            sender.sendMessagePrefixed(TranslationKey.HELP_SPEED);
            return;
        }

        final Optional<String> setting = arguments.next();
        final Optional<String> value = arguments.next();

        if (!setting.isPresent() || !value.isPresent()) {
            sender.sendMessagePrefixed(TranslationKey.HELP_SPEED);
            return;
        }

        switch (setting.get().toLowerCase()) {
            case "chunks":
            case "concurrent":
                final Integer chunks = Input.tryInteger(value.get()).orElse(null);
                if (chunks == null || chunks < 1 || chunks > 500) {
                    sender.sendMessagePrefixed(TranslationKey.HELP_SPEED_CHUNKS);
                    return;
                }
                chunky.getConfig().setMaxConcurrentChunks(chunks);
                sender.sendMessagePrefixed(TranslationKey.FORMAT_SPEED_CHUNKS_SET, chunks);
                break;

            case "delay":
                final Integer delay = Input.tryInteger(value.get()).orElse(null);
                if (delay == null || delay < 0 || delay > 10000) {
                    sender.sendMessagePrefixed(TranslationKey.HELP_SPEED_DELAY);
                    return;
                }
                chunky.getConfig().setChunkGenerationDelay(delay);
                sender.sendMessagePrefixed(TranslationKey.FORMAT_SPEED_DELAY_SET, delay);
                break;

            default:
                sender.sendMessagePrefixed(TranslationKey.HELP_SPEED);
                break;
        }
    }

    @Override
    public List<String> suggestions(final CommandArguments arguments) {
        if (arguments.size() == 1) {
            return List.of("chunks", "concurrent", "delay");
        }
        return Collections.emptyList();
    }
}