package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class BookshelfMagic extends Magic {

	private SoundEvent sound;
	private Item generated;

	public BookshelfMagic(MagicType<? extends BookshelfMagic> type) {
		super(type);
	}

	public BookshelfMagic setAdditionalParams(Item generated, SoundEvent sound) {
		this.generated = generated;
		this.sound = sound;
		return this;
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, generated, "generated");
		MagicUtil.write(json, sound, "sound");
	}

	@Override
	protected void readAdditional(JsonObject json) {
		generated = MagicUtil.read(json, ForgeRegistries.ITEMS, "generated");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound");
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		MagicUtil.encode(buffer, generated);
		MagicUtil.encode(buffer, sound);
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		generated = MagicUtil.decode(buffer);
		sound = MagicUtil.decode(buffer);
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { generated.getDescription() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return getNameArgs();
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::spinMagic;
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
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide) {
			cost(player);
			if (count % 10 == 0) {
				BehaviorUtils.throwItem(player, new ItemStack(generated), nearbyPosition(player));
				playSoundServer(level, player, sound, 1, soundPitch(player));
			}
		} else {
			addParticle(level, player);
		}
	}

	private void addParticle(Level level, Player player) {
		Vec3 pos = player.position().add((player.getRandom().nextDouble() - 0.5) * 2,
				player.getRandom().nextDouble() * 1.5, (player.getRandom().nextDouble() - 0.5) * 2);
		double speedX = (player.getRandom().nextDouble() - 0.5) * 2;
		double speedY = player.getRandom().nextDouble() - 0.5;
		double speedZ = (player.getRandom().nextDouble() - 0.5) * 2;

		level.addParticle(ParticleTypes.ENCHANT, pos.x(), pos.y() + 1, pos.z(), speedX, speedY, speedZ);
	}

	private Vec3 nearbyPosition(Player player) {
		double x = (player.getRandom().nextDouble() - 0.5) * 0.3;
		double y = 0;
		double z = (player.getRandom().nextDouble() - 0.5) * 0.3;
		return player.position().add(x, y, z);
	}

}
