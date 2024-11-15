package gtc_expansion.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import com.cleanroommc.groovyscript.documentation.linkgenerator.LinkGeneratorHooks;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import gtc_expansion.GTCExpansion;

public class GSPlugin implements GroovyPlugin {
    @GroovyBlacklist
    public static GSContainer instance;

    @Override
    public @Nullable GroovyPropertyContainer createGroovyPropertyContainer() {
        GSPlugin.instance = new GSContainer();
        return GSPlugin.instance;
    }

    @Override
    public String getModId() {
        return GTCExpansion.MODID;
    }

    @Override
    public String getContainerName() {
        return GTCExpansion.MODNAME;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> container) {
        LinkGeneratorHooks.registerLinkGenerator(new GTCXLinkGenerator());
    }
}
