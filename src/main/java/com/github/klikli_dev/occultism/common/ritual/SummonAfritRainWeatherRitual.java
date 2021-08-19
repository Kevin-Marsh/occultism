/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.common.ritual;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.SpiritJob;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlBlockEntity;
import com.github.klikli_dev.occultism.registry.*;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

public class SummonAfritRainWeatherRitual extends SummonSpiritRitual {

    //region Initialization
    public SummonAfritRainWeatherRitual() {
        super(null,
                OccultismRituals.SUMMON_AFRIT_PENTACLE.get(),
                Ingredient.of(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                "summon_afrit_rain_weather", 60);
        this.sacrificePredicate =
                (entity) -> OccultismTags.COWS.contains(entity.getType());
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity BlockEntity,
                       Player castingPlayer, ItemStack activationItem) {

        super.finish(level, goldenBowlPosition, BlockEntity, castingPlayer, activationItem);

        //consume activation item
        ItemStack copy = activationItem.copy();
        activationItem.shrink(1);

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //set up the afrit entity
        SpiritEntity spirit = OccultismEntities.AFRIT.get().create(level);
        this.prepareSpiritForSpawn(spirit, level, goldenBowlPosition, castingPlayer,
                ItemNBTUtil.getBoundSpiritName(copy));

        //set up the job
        SpiritJob job = OccultismSpiritJobs.RAIN_WEATHER.get().create(spirit);
        job.init();
        spirit.setJob(job);
        spirit.setSpiritMaxAge(120);

        //notify players nearby and spawn
        this.spawnEntity(spirit, level);
    }

    @Override
    public boolean identify(Level level, BlockPos goldenBowlPosition, ItemStack activationItem) {
        return Occultism.SERVER_CONFIG.rituals.enableRainWeatherRitual.get() &&
               super.identify(level, goldenBowlPosition, activationItem);
    }
    //endregion Overrides
}
