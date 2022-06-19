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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

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
		wisdoms = MagicUtil.readColl(json, "wisdoms", e -> GsonHelper.convertToString(e, "wisdom"), new ArrayList<>());
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "wisdoms", wisdoms, JsonPrimitive::new);
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		String wisdom = wisdoms.get(player.getRandom().nextInt(wisdoms.size()));
		player.playSound(ModSounds.SCRIBBLE, 1, soundPitch(player));
		if (!level.isClientSide) {
			WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
				cost(player);
				ItemStack book = h.extractCurrent();
				CompoundTag tag = book.getOrCreateTag();
				ListTag pages = new ListTag();
				pages.add(StringTag.valueOf(wisdom));
				tag.put("pages", pages);
				book = new ItemStack(Items.WRITABLE_BOOK);
				book.setTag(tag);
				h.insertCurrent(book);
			});
		}
		return super.magicFinish(level, player, staff);
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
