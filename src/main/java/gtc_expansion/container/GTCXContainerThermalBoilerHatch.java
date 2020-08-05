package gtc_expansion.container;

import gtc_expansion.GTCExpansion;
import gtc_expansion.tile.multi.GTCXTileMultiThermalBoiler;
import gtc_expansion.util.GTCXTank;
import gtclassic.api.gui.GTGuiCompFluidTank;
import ic2.core.inventory.container.ContainerTileComponent;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.slots.SlotDisplay;
import ic2.core.inventory.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTCXContainerThermalBoilerHatch extends ContainerTileComponent<GTCXTileMultiThermalBoiler> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(GTCExpansion.MODID, "textures/gui/hatchitemfluid.png");

    public GTCXContainerThermalBoilerHatch(InventoryPlayer player, GTCXTileMultiThermalBoiler tile, boolean second, boolean input) {
        super(tile);
        this.addSlotToContainer(new SlotDisplay(tile, 7, 80, 17));
        this.addSlotToContainer(new SlotOutput(player.player, tile, !input ? second ? 2 : 1 : 7, 80, 53));
        int display = input ? (second ? 4 : 3) : (second ? 6 : 5);
        this.addSlotToContainer(new SlotDisplay(tile, display, 59, 42));
        GTCXTank tank = input ? (second ? tile.getInputTank2() : tile.getInputTank1()) : (second ? tile.getOutputTank2() : tile.getOutputTank1());
        this.addComponent(new GTGuiCompFluidTank(tank));
        this.addPlayerInventory(player, 0, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiLoaded(GuiIC2 gui) {
        gui.disableName();
        gui.dissableInvName();
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return getGuiHolder().canInteractWith(player);
    }

    @Override
    public int guiInventorySize() {
        return 3;
    }
}
