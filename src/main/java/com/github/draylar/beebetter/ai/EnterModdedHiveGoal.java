package com.github.draylar.beebetter.ai;

import com.github.draylar.beebetter.entity.ModdedBeehiveBlockEntity;
import com.github.draylar.beebetter.util.IBeeAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.passive.BeeEntity;

public class EnterModdedHiveGoal extends NotAngryGoal {

    public EnterModdedHiveGoal(BeeEntity bee) {
        super(bee);
    }

    @Override
    public boolean canBeeStart() {
        IBeeAccessor beeAccessor = (IBeeAccessor) this.getBee();

        if (this.getBee().hasHive() && beeAccessor.canBeeEnterHive() && beeAccessor.getHivePos().isWithinDistance(this.getBee().getPos(), 2.0D)) {
            BlockEntity blockEntity = this.getBee().world.getBlockEntity(beeAccessor.getHivePos());

            if (blockEntity instanceof ModdedBeehiveBlockEntity) {
                ModdedBeehiveBlockEntity beeHiveBlockEntity = (ModdedBeehiveBlockEntity) blockEntity;

                if (!beeHiveBlockEntity.isFullOfBees()) {
                    return true;
                }

                beeAccessor.setHivePos(null);
            }
        }

        return false;
    }

    @Override
    public boolean canBeeContinue() {
        return false;
    }

    @Override
    public void start() {
        IBeeAccessor beeAccessor = (IBeeAccessor) this.getBee();

        BlockEntity blockEntity = this.getBee().world.getBlockEntity(beeAccessor.getHivePos());

        if (blockEntity instanceof ModdedBeehiveBlockEntity) {
            ModdedBeehiveBlockEntity beeHiveBlockEntity = (ModdedBeehiveBlockEntity) blockEntity;
            beeHiveBlockEntity.tryEnterHive(this.getBee(), this.getBee().hasNectar());
        }
    }
}
