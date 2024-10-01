package ua.pp.lumivoid.extendedcommandhistory.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.CommandHistoryManager;
import net.minecraft.util.collection.ArrayListDeque;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(CommandHistoryManager.class)
public class CommandHistoryMixin {
    @Unique
    private static final Integer MAX_HISTORY_SIZE = 500;

    @Shadow
    private final ArrayListDeque<String> history = new ArrayListDeque<>(MAX_HISTORY_SIZE);

    @Shadow
    private void write() {
    }

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public void add(String command) {
        if (!command.equals(this.history.peekLast())) {
            if (this.history.size() >= MAX_HISTORY_SIZE) {
                this.history.removeFirst();
            }

            this.history.addLast(command);
            this.write();
        }
    }
}