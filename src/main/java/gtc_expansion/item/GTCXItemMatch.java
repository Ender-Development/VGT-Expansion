package gtc_expansion.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GTCXItemMatch extends GTCXItemMisc {

    public GTCXItemMatch() {
        super("match", 15, 2);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else
        {
            pos = pos.offset(facing);
            ItemStack itemstack = player.getHeldItem(hand);

            if (!player.canPlayerEdit(pos, facing, itemstack))
            {
                return EnumActionResult.FAIL;
            }
            else
            {
                if (worldIn.getBlockState(pos).getMaterial() == Material.AIR)
                {
                    worldIn.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
                    worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
                }

                if (!player.capabilities.isCreativeMode)
                {
                    itemstack.shrink(1);
                }

                return EnumActionResult.SUCCESS;
            }
        }
    }
}
