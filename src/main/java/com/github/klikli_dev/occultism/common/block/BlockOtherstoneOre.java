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

package com.github.klikli_dev.occultism.common.block;

import com.github.klikli_dev.occultism.api.common.block.IOtherOre;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockOtherstoneOre extends Block implements IOtherOre {
    //region Fields

    //endregion Fields

    //region Initialization
    public BlockOtherstoneOre() {
        super(Material.ROCK);
        BlockRegistry.registerBlock(this, "otherstone_ore", Material.ROCK, SoundType.STONE, 1.5f, 30);
        this.setTranslationKey("stone.andesite");
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(UNCOVERED, false));
    }
    //endregion Initialization

    //region Overrides
    @Override
    public Block getUncoveredBlock() {
        return BlockRegistry.OTHERSTONE;
    }

    @Override
    public Block getCoveredBlock() {
        return Blocks.STONE;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return 0;
    }

    @Override
    public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return IOtherOre.super.getActualState(state, worldIn, pos);
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return IOtherOre.super.getItemDropped(state, rand, fortune);
    }

    @Override
    public int damageDropped(BlockState state) {
        return state.getValue(UNCOVERED) ? 0 : BlockStone.EnumType.ANDESITE.getMetadata();
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state,
                             @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, IOtherOre.super.getHarvestState(player, state), te, stack);
    }

    @Override
    public ItemStack getSilkTouchDrop(BlockState state) {
        return IOtherOre.super.getSilkTouchDrop(state);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        return IOtherOre.super.getItem(worldIn, pos, state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, UNCOVERED);
    }
    //endregion Overrides
}
