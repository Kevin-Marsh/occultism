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

package com.github.klikli_dev.occultism.common.world.cave;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.TreeFeature;

/**
 * A tree generator that supports vines without growing cocoa
 */
public class UndergroundGroveTreeGen extends TreeFeature {
    //region Initialization
    public UndergroundGroveTreeGen(boolean notify, int minTreeHeightIn, BlockState woodBlock, BlockState leafBlock,
                                   boolean growVines) {
        super(notify, minTreeHeightIn, woodBlock, leafBlock, growVines);
    }
    //endregion Initialization

    //region Overrides
    @Override
    protected void setBlockAndNotifyAdequately(World worldIn, BlockPos pos, BlockState state) {
        if (state.getBlock() != Blocks.COCOA) //we do not generate cocoa
            super.setBlockAndNotifyAdequately(worldIn, pos, state);
    }
    //endregion Overrides
}
