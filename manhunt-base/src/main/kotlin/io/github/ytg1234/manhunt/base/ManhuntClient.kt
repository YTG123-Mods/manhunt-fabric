package io.github.ytg1234.manhunt.base

import io.github.ytg1234.manhunt.base.init.ManhuntEventRegistration
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer

/**
 * Initializes client-side things.
 *
 * @author YTG1234
 */
@Environment(EnvType.CLIENT)
object ManhuntClient : ClientModInitializer {
    override fun onInitializeClient(mod: ModContainer?) {
        ManhuntEventRegistration.registerClientSideEvents()
    }
}
