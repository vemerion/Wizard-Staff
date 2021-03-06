package mod.vemerion.wizardstaff.Magic.fashionupdate;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import mod.vemerion.wizardstaff.staff.WizardStaffItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class TransmutationMagic extends Magic {

	private Item created;
	private SoundEvent sound;

	public TransmutationMagic(MagicType<? extends TransmutationMagic> type) {
		super(type);
	}
	
	public TransmutationMagic setAdditionalParams(Item created, SoundEvent sound) {
		this.created = created;
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
	protected void decodeAdditional(PacketBuffer buffer) {
		created = buffer.readItemStack().getItem();
		sound = MagicUtil.decode(buffer, ForgeRegistries.SOUND_EVENTS);
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		buffer.writeItemStack(created.getDefaultInstance());
		MagicUtil.encode(buffer, sound);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		created = JSONUtils.getItem(json, "created");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound", ModSounds.PLOP);
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, created, "created");
		MagicUtil.write(json, sound, "sound");
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { created.getDisplayName(created.getDefaultInstance()) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { created.getDisplayName(created.getDefaultInstance()) };
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(sound, 1, soundPitch(player));
		if (!world.isRemote) {
			WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
				cost(player);
				ItemStack extracted = h.extractCurrent();
				ItemStack createdStack = new ItemStack(created);
				CompoundNBT tag = extracted.getOrCreateTag();
				if (tag.contains("display")) {
					CompoundNBT display = tag.getCompound("display");
					int color = display.getInt("color");

					tag = createdStack.getOrCreateTag();
					display = createdStack.getOrCreateChildTag("display");
					display.putInt("color", color);
				}
				h.insertCurrent(createdStack);
			});
		}
		return super.magicFinish(world, player, staff);
	}

}
