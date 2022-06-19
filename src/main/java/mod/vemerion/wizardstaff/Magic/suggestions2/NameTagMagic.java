package mod.vemerion.wizardstaff.Magic.suggestions2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class NameTagMagic extends Magic {

	private List<String> syllables;

	public NameTagMagic(MagicType<? extends NameTagMagic> type) {
		super(type);
	}

	public NameTagMagic setAdditionalParams(List<String> syllables) {
		this.syllables = syllables;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		syllables = MagicUtil.readColl(json, "syllables", e -> GsonHelper.convertToString(e, "syllable"), new ArrayList<>());
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "syllables", syllables, s -> new JsonPrimitive(s));
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildupMagic;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::spinMagic;
	}

	private String randomName(Random rand) {
		String name = "";
		for (int i = 0; i < rand.nextInt(3) + 2; i++) {
			name += syllables.get(rand.nextInt(syllables.size()));
		}
		return StringUtils.capitalize(name);
	}

	@Override
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		player.playSound(ModSounds.SCRIBBLE, 1, soundPitch(player));
		if (!level.isClientSide) {
			WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
				String name = randomName(player.getRandom());
				cost(player);
				ItemStack nametag = h.extractCurrent();
				nametag = Items.NAME_TAG.getDefaultInstance();
				nametag.setHoverName(new TextComponent(name));
				h.insertCurrent(nametag);
			});
		}
		return super.magicFinish(level, player, staff);
	}

}
