package in.kairoku.skillset_centimental.networking;

import in.kairoku.skillset_centimental.entity.PortalEntity;
import in.kairoku.skillset_centimental.item.ChorusOfTheVoidItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.PlayPacketHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class SummonPortalPacketHandler implements PlayPacketHandler<SummonPortalPacket> {

    @Override
    public void receive(SummonPortalPacket packet, ServerPlayerEntity player, PacketSender responseSender) {

    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf bug, PacketSender sender) {
        if(player.getMainHandStack().getItem() instanceof ChorusOfTheVoidItem) PortalEntity.summon(player);
    }



}