package gtc_expansion.proxy;

import gtc_expansion.GTBlocks2;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GTProxyCommon2 {

    public void preInit(FMLPreInitializationEvent e) {
        GTBlocks2.registerBlocks();
        GTBlocks2.registerTiles();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}