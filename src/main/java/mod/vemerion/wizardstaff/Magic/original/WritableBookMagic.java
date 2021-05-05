package mod.vemerion.wizardstaff.Magic.original;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.world.World;

public class WritableBookMagic extends Magic {

	private List<String> wisdoms;

	public WritableBookMagic(MagicType<? extends WritableBookMagic> type) {
		super(type);
	}

	public WritableBookMagic setAdditionalParams(List<String> wisdoms) {
		this.wisdoms = wisdoms;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		wisdoms = MagicUtil.readColl(json, "wisdoms", e -> JSONUtils.getString(e, "wisdom"), new ArrayList<>());
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "wisdoms", wisdoms, JsonPrimitive::new);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		String wisdom = wisdoms.get(player.getRNG().nextInt(wisdoms.size()));
		player.playSound(ModSounds.SCRIBBLE, 1, soundPitch(player));
		if (!world.isRemote) {
			cost(player);
			WizardStaffItemHandler handler = WizardStaffItemHandler.get(staff);
			ItemStack book = handler.extractItem(0, 1, false);
			CompoundNBT tag = book.getOrCreateTag();
			ListNBT pages = new ListNBT();
			pages.add(StringNBT.valueOf(wisdom));
			tag.put("pages", pages);
			book = new ItemStack(Items.WRITABLE_BOOK);
			book.setTag(tag);
			handler.insertItem(0, book, false);
		}
		return super.magicFinish(world, player, staff);
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}
}
