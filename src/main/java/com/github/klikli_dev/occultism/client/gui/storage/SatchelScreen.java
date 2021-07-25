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

package com.github.klikli_dev.occultism.client.gui.storage;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.container.storage.SatchelContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class SatchelScreen extends ContainerScreen<SatchelContainer> {
    //region Fields
    protected static final ResourceLocation BACKGROUND = new ResourceLocation(Occultism.MODID,
            "textures/gui/inventory_satchel.png");
    //endregion Fields

    //region Initialization
    public SatchelScreen(SatchelContainer screenContainer, Inventory inv,
                         Component titleIn) {
        super(screenContainer, inv, titleIn);

        this.imageWidth = 247;
        this.imageHeight = 256;

    }
    //endregion Initialization


    //region Overrides
    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    public void drawGuiContainerForegroundLayer(PoseStack poseStack, int mouseX, int mouseY) {
        //Note: Do not call super.drawGuiContainerForegroundLayer(poseStack, mouseX, mouseY);
        //      it renders inventory titles which no vanilla inventory does
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(PoseStack poseStack, float partialTicks, int mouseX,
                                                   int mouseY) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(BACKGROUND);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
    //endregion Overrides
}
