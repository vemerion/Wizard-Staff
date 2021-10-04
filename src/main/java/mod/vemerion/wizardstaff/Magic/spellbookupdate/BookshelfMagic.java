package mod.vemerion.wizardstaff.Magic.spellbookupdate;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
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
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, generated);
		MagicUtil.encode(buffer, sound);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		generated = MagicUtil.decode(buffer, ForgeRegistries.ITEMS);
		sound = MagicUtil.decode(buffer, ForgeRegistries.SOUND_EVENTS);
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { generated.getName() };
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
		if (!world.isRemote) {
			cost(player);
			if (count % 10 == 0) {
				BrainUtil.spawnItemNearEntity(player, new ItemStack(generated), nearbyPosition(player));
				playSoundServer(world, player, sound, 1, soundPitch(player));
			}
		} else {
			addParticle(world, player);
		}
	}

	private void addParticle(World world, PlayerEntity player) {
		Vector3d pos = player.getPositionVec().add((player.getRNG().nextDouble() - 0.5) * 2,
				player.getRNG().nextDouble() * 1.5, (player.getRNG().nextDouble() - 0.5) * 2);
		double speedX = (player.getRNG().nextDouble() - 0.5) * 2;
		double speedY = player.getRNG().nextDouble() - 0.5;
		double speedZ = (player.getRNG().nextDouble() - 0.5) * 2;

		world.addParticle(ParticleTypes.ENCHANT, pos.getX(), pos.getY() + 1, pos.getZ(), speedX, speedY, speedZ);
	}

	private Vector3d nearbyPosition(PlayerEntity player) {
		double x = (player.getRNG().nextDouble() - 0.5) * 0.3;
		double y = 0;
		double z = (player.getRNG().nextDouble() - 0.5) * 0.3;
		return player.getPositionVec().add(x, y, z);
	}

}
