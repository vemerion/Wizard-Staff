package mod.vemerion.wizardstaff.Magic.restructuring;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionMagic extends Magic {

	private int level;
	private int potionTime;
	private float radius;
	private Effect potion;
	private boolean affectCaster;
	private SoundEvent sound;

	public PotionMagic(MagicType<? extends PotionMagic> type) {
		super(type);
	}
	
	public PotionMagic setAdditionalParams(int level, int potionTime, float radius, Effect potion, boolean affectCaster, SoundEvent sound) {
		this.level = level;
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		level = JSONUtils.getInt(json, "level");
		potionTime = JSONUtils.getInt(json, "potion_time");
		radius = JSONUtils.getFloat(json, "radius");
		potion = MagicUtil.read(json, ForgeRegistries.POTIONS, "potion");
		affectCaster = JSONUtils.getBoolean(json, "affect_caster");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("level", level);
		json.addProperty("potion_time", potionTime);
		json.addProperty("radius", radius);
		MagicUtil.write(json, potion, "potion");
		json.addProperty("affect_caster", affectCaster);
		MagicUtil.write(json, sound, "sound");
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		level = buffer.readInt();
		potionTime = buffer.readInt();
		radius = buffer.readFloat();
		potion = MagicUtil.decode(buffer, ForgeRegistries.POTIONS);
		affectCaster = buffer.readBoolean();
		sound = MagicUtil.decode(buffer, ForgeRegistries.SOUND_EVENTS);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		buffer.writeInt(level);
		buffer.writeInt(potionTime);
		buffer.writeFloat(radius);
		MagicUtil.encode(buffer, potion);
		buffer.writeBoolean(affectCaster);
		MagicUtil.encode(buffer, sound);
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
		args[2] = new StringTextComponent(String.valueOf(potionTime / 20));
		ITextComponent glue = radius > 0.001
				? new TranslationTextComponent("gui.wizard-staff.potion_magic.glue")
				: StringTextComponent.EMPTY;
		args[3] = affectCaster ? new TranslationTextComponent("gui.wizard-staff.potion_magic.affect_caster", glue)
				: StringTextComponent.EMPTY;
		args[4] = radius > 0.001 ? new TranslationTextComponent("gui.wizard-staff.potion_magic.affect_others")
				: StringTextComponent.EMPTY;

		return args;
	}

	private TextComponent levelTranslation() {
		if (level <= 0)
			return new TranslationTextComponent("enchantment.level.1");
		else if (level <= 10)
			return new TranslationTextComponent("enchantment.level." + level);
		else
			return new StringTextComponent(String.valueOf(level));
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			cost(player);

			if (affectCaster)
				player.addPotionEffect(new EffectInstance(potion, potionTime, level - 1));

			if (radius > 0.001) {
				AxisAlignedBB box = new AxisAlignedBB(player.getPositionVec(), player.getPositionVec()).grow(radius);
				for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, box, e -> e != player)) {
					e.addPotionEffect(new EffectInstance(potion, potionTime, level - 1));
				}
			}
		}

		player.playSound(sound, 1, soundPitch(player));
		return super.magicFinish(world, player, staff);
	}
}
