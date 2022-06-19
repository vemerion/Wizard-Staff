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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
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
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		created = buffer.readItem().getItem();
		sound = MagicUtil.decode(buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeItem(created.getDefaultInstance());
		MagicUtil.encode(buffer, sound);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		created = GsonHelper.getAsItem(json, "created");
		sound = MagicUtil.read(json, ForgeRegistries.SOUND_EVENTS, "sound", ModSounds.PLOP);
	}
	
	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, created, "created");
		MagicUtil.write(json, sound, "sound");
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { created.getName(created.getDefaultInstance()) };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { created.getName(created.getDefaultInstance()) };
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		player.playSound(sound, 1, soundPitch(player));
		if (!level.isClientSide) {
			WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
				cost(player);
				ItemStack extracted = h.extractCurrent();
				ItemStack createdStack = new ItemStack(created);
				CompoundTag tag = extracted.getOrCreateTag();
				if (tag.contains("display")) {
					CompoundTag display = tag.getCompound("display");
					int color = display.getInt("color");

					tag = createdStack.getOrCreateTag();
					display = createdStack.getOrCreateTagElement("display");
					display.putInt("color", color);
				}
				h.insertCurrent(createdStack);
			});
		}
		return super.magicFinish(level, player, staff);
	}

}
