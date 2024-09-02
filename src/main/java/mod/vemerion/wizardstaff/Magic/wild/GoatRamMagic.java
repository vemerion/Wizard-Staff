package mod.vemerion.wizardstaff.Magic.wild;

import java.util.List;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class GoatRamMagic extends Magic {

	private int range;

	public GoatRamMagic(MagicType<? extends GoatRamMagic> type) {
		super(type);
	}

	public GoatRamMagic setAdditionalParams(int range) {
		this.range = range;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		range = GsonHelper.getAsInt(json, "range");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("range", range);
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
	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		if (!level.isClientSide) {
			List<Goat> goats = level.getEntitiesOfClass(Goat.class, new AABB(player.blockPosition()).inflate(range),
					e -> true);
			if (!goats.isEmpty())
				cost(player);
			for (var goat : goats) {
				goat.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, 0);
				goat.getBrain().setMemory(MemoryModuleType.RAM_TARGET, player.position());
			}
			playSoundServer(level, player, getSound(!goats.isEmpty(), player.getRandom()), cost, soundPitch(player));
		}

		return super.magicFinish(level, player, staff);
	}

	private SoundEvent getSound(boolean success, RandomSource rand) {
		if (!success)
			return ModSounds.POOF.get();

		var sounds = Registry.INSTRUMENT.getTag(InstrumentTags.GOAT_HORNS);

		return sounds.flatMap(e -> e.getRandomElement(rand)).map(e -> e.get().soundEvent())
				.orElse(SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(0));
	}
}
