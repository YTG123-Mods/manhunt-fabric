package io.github.ytg1234.manhunt.base

import io.github.ytg1234.manhunt.base.init.ManhuntEventRegistration
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object ManhuntClient : ClientModInitializer {
    override fun onInitializeClient() {
        ManhuntEventRegistration.registerClientSideEvents()
    }
}
