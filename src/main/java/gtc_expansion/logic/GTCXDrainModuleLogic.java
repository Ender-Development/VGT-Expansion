package gtc_expansion.logic;

import gtc_expansion.tile.pipes.GTCXTileBasePipe;
import net.minecraft.entity.player.EntityPlayer;

public class GTCXDrainModuleLogic extends GTCXBaseCoverLogic {
    public GTCXDrainModuleLogic(GTCXTileBasePipe pipe) {
        super(pipe);
    }

    @Override
    public void onTick() {

    }

    @Override
    public boolean cycleMode(EntityPlayer player) {
        return false;
    }
}
