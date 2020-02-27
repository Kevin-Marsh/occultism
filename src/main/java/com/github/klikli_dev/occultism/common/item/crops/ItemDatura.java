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

package com.github.klikli_dev.occultism.common.item.crops;

import com.github.klikli_dev.occultism.registry.ItemRegistry;
import com.github.klikli_dev.occultism.registry.PotionRegistry;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.EffectInstance;

public class ItemDatura extends ItemFood {
    //region Initialization
    public ItemDatura() {
        super(0, 0, false);
        this.setPotionEffect(new EffectInstance(PotionRegistry.THIRD_EYE, 15 * 20, 0, false, false), 1.1f);
        this.setAlwaysEdible();
        ItemRegistry.registerItem(this, "datura", "cropDatura");
    }
    //endregion Initialization
}
