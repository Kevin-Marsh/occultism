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

package com.github.klikli_dev.occultism.network;

import com.github.klikli_dev.occultism.common.entity.HeadlessFamiliarEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageHeadlessDie extends MessageBase {

    private int id;

    public MessageHeadlessDie(int id) {
        this.id = id;
    }

    public MessageHeadlessDie(PacketBuffer buf) {
        this.decode(buf);
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.id);
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.id = buf.readInt();
    }

    @Override
    public void onClientReceived(Minecraft minecraft, PlayerEntity player, Context context) {
        Entity headless = minecraft.level.getEntity(id);
        if (headless instanceof HeadlessFamiliarEntity)
            ((HeadlessFamiliarEntity) headless).killHeadless();
    }

}
