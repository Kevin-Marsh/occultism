/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.github.klikli_dev.occultism.handlers;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.common.entity.BeholderFamiliarEntity;
import com.github.klikli_dev.occultism.common.entity.FamiliarEntity;
import com.github.klikli_dev.occultism.common.entity.GuardianFamiliarEntity;
import com.github.klikli_dev.occultism.common.entity.HeadlessFamiliarEntity;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.util.FamiliarUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FamiliarEventHandler {

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        guardianUltimateSacrifice(event);
        headlessStealHead(event);
    }

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player))
            return;
        Player player = (Player) event.getSource().getEntity();

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        EntityType<?> headType = event.getEntityLiving().getType();

        if (!FamiliarUtil.hasFamiliar(player, OccultismEntities.HEADLESS_FAMILIAR.get(),
                h -> h.getHeadType() == headType))
            return;

        event.setAmount(event.getAmount() * 1.3f);
    }

    private static void headlessStealHead(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player))
            return;
        Player player = (Player) event.getSource().getEntity();

        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.HEADLESS_FAMILIAR.get()))
            return;

        List<HeadlessFamiliarEntity> headlesses = FamiliarUtil.getAllFamiliars(player,
                OccultismEntities.HEADLESS_FAMILIAR.get());

        if (!headlesses.isEmpty() && event.getEntityLiving().getType() == OccultismEntities.CTHULHU_FAMILIAR.get())
            OccultismAdvancements.FAMILIAR.trigger(player, FamiliarTrigger.Type.HEADLESS_CTHULHU_HEAD);

        headlesses.forEach(h -> h.setHeadType(event.getEntityLiving().getType()));
    }

    private static void guardianUltimateSacrifice(LivingDeathEvent event) {
        if (event.getSource().isBypassInvul() || !(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        if (!FamiliarUtil.isFamiliarEnabled(player, OccultismEntities.GUARDIAN_FAMILIAR.get()))
            return;

        GuardianFamiliarEntity guardian = FamiliarUtil.getFamiliar(player, OccultismEntities.GUARDIAN_FAMILIAR.get());
        if (guardian == null)
            return;

        if (!guardian.sacrifice())
            return;

        event.setCanceled(true);
        player.setHealth(1);
        player.removeAllEffects();
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 10, 1));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 5, 1));
    }

    @SubscribeEvent
    public static void beholderBlindnessImmune(PotionEvent.PotionApplicableEvent event) {
        if (event.getPotionEffect().getEffect() != MobEffects.BLINDNESS)
            return;

        LivingEntity entity = event.getEntityLiving();
        EntityType<BeholderFamiliarEntity> beholder = OccultismEntities.BEHOLDER_FAMILIAR.get();

        if (!FamiliarUtil.hasFamiliar(entity, beholder, FamiliarEntity::hasBlacksmithUpgrade))
            return;

        event.setResult(Result.DENY);
    }
}