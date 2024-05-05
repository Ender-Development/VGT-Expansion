package gtc_expansion.mixin.gtclassic;

import gtclassic.common.event.GTEventPlayerLogin;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GTEventPlayerLogin.class, remap = false)
public class GTEventPlayerLoginMixin {

    @Inject(method = "onPlayerLogin", at = @At("HEAD"), cancellable = true)
    private void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}
