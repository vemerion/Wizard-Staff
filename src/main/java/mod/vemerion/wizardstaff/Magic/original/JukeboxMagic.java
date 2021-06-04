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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
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
				e -> new ResourceLocation(JSONUtils.getString(e, "entity name")), new HashSet<>());
		range = JSONUtils.getFloat(json, "range");
		music = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "music");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "blacklist", blacklist, rl -> new JsonPrimitive(rl.toString()));
		json.addProperty("range", range);
		MagicUtil.write(json, music, "music");
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		blacklist = MagicUtil.decodeColl(buffer, b -> b.readResourceLocation(), new HashSet<>());
		range = buffer.readFloat();
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encodeColl(buffer, blacklist, (b, rl) -> b.writeResourceLocation(rl));
		buffer.writeFloat(range);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicStart(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote)
			Network.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
					new JukeboxMagicMessage(player.getUniqueID(), music));
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new StringTextComponent(String.valueOf(range)) };
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class,
				player.getBoundingBox().grow(range),
				e -> e != player && !blacklist.contains(e.getType().getRegistryName()));

		for (LivingEntity e : entities) {
			if (!world.isRemote) {
				if (count % 5 == 0)
					e.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 7, 100, false, false, false));
			} else if (e.ticksExisted % 5 == 0) {
				e.limbSwingAmount = 1 + player.getRNG().nextFloat() - 0.5f;
				e.swingArm(Hand.MAIN_HAND);
				e.swingArm(Hand.OFF_HAND);
			}
		}
		if (!world.isRemote && !entities.isEmpty())
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
