package gtc_expansion.mixin;

import com.google.common.collect.ImmutableMap;
import gtclassic.GTMod;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class LateMixin implements ILateMixinLoader {
    private static final Map<String, BooleanSupplier> MIXINS_CONFIG = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>(){
        {
            put("mixin.gtc_expansion.gtclassic.json", () -> Loader.isModLoaded("gtclassic") && GTMod.MODVERSION.equals("1.2.0"));
        }
    });

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(MIXINS_CONFIG.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(String config) {
        return MIXINS_CONFIG.get(config).getAsBoolean();
    }
}
