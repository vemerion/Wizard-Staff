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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

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
		syllables = MagicUtil.readColl(json, "syllables", e -> JSONUtils.getString(e, "syllable"), new ArrayList<>());
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.writeColl(json, "syllables", syllables, s -> new JsonPrimitive(s));
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
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
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		player.playSound(ModSounds.SCRIBBLE, 1, soundPitch(player));
		if (!world.isRemote) {
			WizardStaffItemHandler.getOptional(staff).ifPresent(h -> {
				String name = randomName(player.getRNG());
				cost(player);
				ItemStack nametag = h.extractCurrent();
				nametag = Items.NAME_TAG.getDefaultInstance();
				nametag.setDisplayName(new StringTextComponent(name));
				h.insertCurrent(nametag);
			});
		}
		return super.magicFinish(world, player, staff);
	}

}
