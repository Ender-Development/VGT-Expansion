package gtc_expansion.mixin;

import com.google.common.collect.ImmutableList;
import gtc_expansion.GTCExpansion;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;
import java.util.stream.Collectors;

public class LateMixin implements ILateMixinLoader {
    public static final List<String> modMixins = ImmutableList.of(
            //"ic2c_extras"
    );

    @Override
    public List<String> getMixinConfigs() {
        return modMixins.stream().filter(Loader::isModLoaded).map(mod -> "mixin."+ GTCExpansion.MODID +"." + mod + ".json").collect(Collectors.toList());
    }
}
