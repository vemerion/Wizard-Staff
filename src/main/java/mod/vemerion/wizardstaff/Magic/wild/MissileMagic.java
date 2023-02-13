package mod.vemerion.wizardstaff.Magic.wild;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.entity.MagicMissileEntity;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class MissileMagic extends Magic {

	private int damage;
	private int interval;
	private Item item;

	public MissileMagic(MagicType<? extends MissileMagic> type) {
		super(type);
	}

	public MissileMagic setAdditionalParams(int damage, int interval, Item item) {
		this.damage = damage;
		this.interval = interval;
		this.item = item;
		return this;
	}

	@Override
	protected void readAdditional(JsonObject json) {
		damage = GsonHelper.getAsInt(json, "damage");
		interval = GsonHelper.getAsInt(json, "interval");
		item = MagicUtil.read(json, ForgeRegistries.ITEMS, "item");
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		json.addProperty("damage", damage);
		json.addProperty("interval", interval);
		MagicUtil.write(json, ForgeRegistries.ITEMS, item, "item");
	}

	@Override
	protected void decodeAdditional(FriendlyByteBuf buffer) {
		damage = buffer.readInt();
		interval = buffer.readInt();
		item = MagicUtil.decode(buffer);
	}

	@Override
	protected void encodeAdditional(FriendlyByteBuf buffer) {
		buffer.writeInt(damage);
		buffer.writeInt(interval);
		MagicUtil.encode(buffer, ForgeRegistries.ITEMS, item);
	}

	protected Object[] getNameArgs() {
		return new Object[] { item.getDescription() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { item.getDescription(), interval, damage };
	}

	@Override
	public UseAnim getUseAnim(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::forward;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::forwardShake;
	}

	@Override
	public void magicTick(Level level, Player player, ItemStack staff, int count) {
		if (!level.isClientSide) {
			if ((count + 1) % interval != 0)
				return;

			var direction = Vec3.directionFromRotation(player.getRotationVector());
			var right = direction.yRot(Mth.DEG_TO_RAD * 90);
			var rand = player.getRandom();
			var position = player.position().add(direction.x() * 1 + right.x() * (rand.nextDouble() - 0.5),
					1.2 + rand.nextDouble(), direction.z() * 1 + right.z() * (rand.nextDouble() - 0.5));
			cost(player);
			var missile = new MagicMissileEntity(position.x, position.y, position.z, level, damage);
			missile.setOwner(player);
			missile.setItem(item);
			level.addFreshEntity(missile);
			playSoundServer(level, player, ModSounds.CREATE.get(), 1, soundPitch(player));
		}
	}

}
