package gtc_expansion.groovyscript;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import gtc_expansion.GTCExpansion;

public class GTCXLinkGenerator extends BasicLinkGenerator {
    @Override
    public String id() {
        return GTCExpansion.MODID;
    }

    @Override
    public String domain() {
        return "https://github.com/Ender-Development/VGT-Expansion/";
    }
}
