package in.kairoku.skillset_centimental.networking;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SummonPortalPacket implements FabricPacket {
    public static final Identifier ID = new Identifier(SkillsetCentimental.MOD_ID, "summon_portal");
    public static final PacketType<SummonPortalPacket> TYPE = PacketType.create(ID, SummonPortalPacket::new);

    public SummonPortalPacket() {

    }

    public SummonPortalPacket(PacketByteBuf buf) {
        this();
    }

    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

}