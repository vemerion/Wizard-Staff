package mod.vemerion.wizardstaff.Magic.original;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.network.JukeboxMagicMessage;
import mod.vemerion.wizardstaff.network.Network;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

public class JukeboxMagic extends Magic {

	private Set<ResourceLocation> blacklist;
	private float range;
	private SoundEvent music;

	public JukeboxMagic(MagicType<? extends JukeboxMagic> type) {
		super(type);
	}

	public JukeboxMagic setAdditionalParams(Set<ResourceLocation> blacklist, float range, SoundEvent music) {
		this.blacklist = blacklist;
		this.range = range;
		this.music = music;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		blacklist = MagicUtil.readColl(json, "blacklist",
				e -> new ResourceLocation(GsonHelper.convertToString(e, "entity name")), new HashSet<>());
		range = GsonHelper.getAsFloat(json, "range");
		music = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "music");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "blacklist", blacklist, rl -> new JsonPrimitive(rl.toString()));
		json.addProperty("range", range);
		MagicUtil.write(json, music, "music");
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		blacklist = MagicUtil.decodeColl(buffer, b -> b.readResourceLocation(), new HashSet<>());
		range = buffer.readFloat();
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encodeColl(buffer, blacklist, (b, rl) -> b.writeResourceLocation(rl));
		buffer.writeFloat(range);
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public void magicStart(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide)
			Network.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
					new JukeboxMagicMessage(player.getUUID(), music));
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new TextComponent(String.valueOf(range)) };
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
				player.getBoundingBox().inflate(range),
				e -> e != player && !blacklist.contains(e.getType().getRegistryName()));

		for (LivingEntity e : entities) {
			if (!level.isClientSide) {
				if (count % 5 == 0)
					e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 7, 100, false, false, false));
			} else if (e.tickCount % 5 == 0) {
				e.animationSpeed = 1 + player.getRandom().nextFloat() - 0.5f;
				e.swing(InteractionHand.MAIN_HAND);
				e.swing(InteractionHand.OFF_HAND);
			}
		}
		if (!level.isClientSide && !entities.isEmpty())
			cost(player);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::swinging;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::swinging;
	}

}
