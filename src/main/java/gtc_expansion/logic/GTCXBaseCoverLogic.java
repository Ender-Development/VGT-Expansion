package gtc_expansion.logic;

import gtc_expansion.tile.pipes.GTCXTileBasePipe;
import ic2.api.classic.network.adv.IInputBuffer;
import ic2.api.classic.network.adv.IOutputBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class GTCXBaseCoverLogic {
    protected GTCXTileBasePipe pipe;
    protected EnumFacing facing;
    public GTCXBaseCoverLogic(GTCXTileBasePipe pipe, EnumFacing facing){
        this.pipe = pipe;
        this.facing = facing;
    }
    public abstract void onTick();

    public abstract boolean cycleMode(EntityPlayer player);

    public void readFromNBT(NBTTagCompound nbt) {
    }

    public void writeToNBT(NBTTagCompound nbt) {
    }


    public void read(IInputBuffer buffer) {
    }


    public void write(IOutputBuffer buffer) {
    }

    public boolean allowsPipeOutput(){
        return false;
    }

    public boolean allowsPipeInput(){
        return true;
    }
}
