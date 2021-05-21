package mod.vemerion.wizardstaff.Magic.suggestions2;

import com.google.gson.JsonObject;

import mod.vemerion.wizardstaff.Magic.Magic;
import mod.vemerion.wizardstaff.Magic.MagicType;
import mod.vemerion.wizardstaff.Magic.MagicUtil;
import mod.vemerion.wizardstaff.entity.MagicVexEntity;
import mod.vemerion.wizardstaff.init.ModSounds;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonEntityMagic extends Magic {

	private EntityType<?> entity;
	private int count;

	public SummonEntityMagic(MagicType<? extends SummonEntityMagic> type) {
		super(type);
	}

	public SummonEntityMagic setAdditionalParams(EntityType<?> entity, int count) {
		this.entity = entity;
		this.count = count;
		return this;
	}

	@Override
	protected void writeAdditional(JsonObject json) {
		MagicUtil.write(json, entity, "entity");
		json.addProperty("count", count);
	}

	@Override
	protected void readAdditional(JsonObject json) {
		entity = MagicUtil.read(json, ForgeRegistries.ENTITIES, "entity");
		count = JSONUtils.getInt(json, "count");
	}

	@Override
	protected void encodeAdditional(PacketBuffer buffer) {
		MagicUtil.encode(buffer, entity);
		buffer.writeInt(count);
	}

	@Override
	protected void decodeAdditional(PacketBuffer buffer) {
		entity = MagicUtil.decode(buffer, ForgeRegistries.ENTITIES);
		count = buffer.readInt();
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}

	@Override
	protected Object[] getNameArgs() {
		return new Object[] { entity.getName() };
	}

	@Override
	protected Object[] getDescrArgs() {
		return new Object[] { new StringTextComponent(String.valueOf(count)), entity.getName() };
	}

	@Override
	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		if (!world.isRemote) {
			cost(player);
			for (int i = 0; i < count; i++) {
				Entity e = entity.create(world);
				Vector3d pos = nearby(player);
				e.setPositionAndRotation(pos.x, pos.y, pos.z, player.getRNG().nextFloat() * 360, 0);

				if (e instanceof MagicVexEntity) {
					MagicVexEntity vex = (MagicVexEntity) e;
					vex.setCaster(player);
					vex.setLimitedLife(20 * 20);
				}
				world.addEntity(e);
			}
		}
		player.playSound(ModSounds.BELL, 1, soundPitch(player));
		return super.magicFinish(world, player, staff);
	}

	private Vector3d nearby(PlayerEntity player) {
		return player.getPositionVec().add(randCoord(player), randCoord(player) + 1, randCoord(player));
	}

	private double randCoord(PlayerEntity player) {
		return (player.getRNG().nextDouble() - 0.5) * 2;
	}

	@Override
	public RenderFirstPersonMagic firstPersonRenderer() {
		return WizardStaffTileEntityRenderer::buildup;
	}

	@Override
	public RenderThirdPersonMagic thirdPersonRenderer() {
		return WizardStaffLayer::buildup;
	}

}
