package gtc_expansion.recipes;

import gtc_expansion.tile.GTTileElectrolyzer;
import gtc_expansion.tile.multi.GTTileMultiVacuumFreezer;

public class GTRecipe2 {
    public static void init(){
        GTRecipeRemove.initCentrifugeRemoval();
        GTRecipeCentrifuge.init();
        GTTileElectrolyzer.init();
        GTTileMultiVacuumFreezer.init();
        GTRecipeIterators2.init();
    }

    public static void postInit(){
        GTRecipeRemove.initBlastFurnaceRemoval();
        GTRecipeBlastFurnace.removals();
        GTRecipeBlastFurnace.init();
    }
}
