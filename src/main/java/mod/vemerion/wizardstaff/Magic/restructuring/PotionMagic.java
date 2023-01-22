package mod.vemerion.wizardstaff.Magic.restructuring;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionMagic extends Magic {

	private int amplifier;
	private int potionTime;
	private float radius;
	private MobEffect potion;
	private boolean affectCaster;
	private SoundEvent sound;

	public PotionMagic(MagicType<? extends PotionMagic> type) {
		super(type);
	}
	
	public PotionMagic setAdditionalParams(int amplifier, int potionTime, float radius, MobEffect potion, boolean affectCaster, SoundEvent sound) {
		this.amplifier = amplifier;
		this.potionTime = potionTime;
		this.radius = radius;
		this.potion = potion;
		this.affectCaster = affectCaster;
		this.sound = sound;
		return this;
	}
	
	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		amplifier = GsonHelper.getAsInt(json, "level");
		potionTime = GsonHelper.getAsInt(json, "potion_time");
		radius = GsonHelper.getAsFloat(json, "radius");
		potion = MagicUtil.read(json, ForgeRegistries.MOB_EFFECTS, "potion");
		affectCaster = GsonHelper.getAsBoolean(json, "affect_caster");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("level", amplifier);
		json.addProperty("potion_time", potionTime);
		json.addProperty("radius", radius);
		MagicUtil.write(json, ForgeRegistries.MOB_EFFECTS, potion, "potion");
		json.addProperty("affect_caster", affectCaster);
		MagicUtil.write(json, ForgeRegistries.SOUND_EVENTS, sound, "sound");
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		amplifier = buffer.readInt();
		potionTime = buffer.readInt();
		radius = buffer.readFloat();
		potion = MagicUtil.decode(buffer);
		affectCaster = buffer.readBoolean();
		sound = MagicUtil.decode(buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeInt(amplifier);
		buffer.writeInt(potionTime);
		buffer.writeFloat(radius);
		MagicUtil.encode(buffer, ForgeRegistries.MOB_EFFECTS, potion);
		buffer.writeBoolean(affectCaster);
		MagicUtil.encode(buffer, ForgeRegistries.SOUND_EVENTS, sound);
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { potion.getDisplayName(), levelTranslation() };
	}

	@Override
	protected Object[] getDescrArgs() {
		Object[] args = new Object[5];
		args[0] = potion.getDisplayName();
		args[1] = levelTranslation();
		args[2] = Component.literal(String.valueOf(potionTime / 20));
		Component glue = radius > 0.001
				? Component.translatable("gui.wizardstaff.potion_magic.glue")
				: Component.empty();
		args[3] = affectCaster ? Component.translatable("gui.wizardstaff.potion_magic.affect_caster", glue)
				: Component.empty();
		args[4] = radius > 0.001 ? Component.translatable("gui.wizardstaff.potion_magic.affect_others")
				: Component.empty();

		return args;
	}

	private Component levelTranslation() {
		if (amplifier <= 0)
			return Component.translatable("enchantment.level.1");
		else if (amplifier <= 10)
			return Component.translatable("enchantment.level." + amplifier);
		else
			return Component.literal(String.valueOf(amplifier));
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			cost(player);

			if (affectCaster)
				player.addEffect(new MobEffectInstance(potion, potionTime, amplifier - 1));

			if (radius > 0.001) {
				AABB box = new AABB(player.position(), player.position()).inflate(radius);
				for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, e -> e != player)) {
					e.addEffect(new MobEffectInstance(potion, potionTime, amplifier - 1));
				}
			}
		}

		player.playSound(sound, 1, soundPitch(player));
		return super.magicFinish(level, player, staff);
	}
}
