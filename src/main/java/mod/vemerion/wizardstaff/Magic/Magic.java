package mod.vemerion.wizardstaff.Magic;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.item.MagicArmorItem;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public abstract class Magic {

	public static final int HOUR = 72000;

	protected float cost;
	protected int duration;
	protected Ingredient ingredient;
	private MagicType<?> type;

	public Magic(MagicType<?> type) {
		this.type = type;
	}
	
	public Magic setParams(float cost, int duration, Ingredient ingredient) {
		this.cost = cost;
		this.duration = duration;
		this.ingredient = ingredient;
		return this;
	}

	public ResourceLocation getRegistryName() {
		return type.getRegistryName();
	}

	public void read(JsonObject json) {
		cost = JSONUtils.getFloat(json, "cost");
		if (cost < 0)
			throw new JsonSyntaxException("The cost of a magic can not be negative");
		duration = JSONUtils.getInt(json, "duration");
		if (duration < 0)
			duration = HOUR;
		ingredient = Ingredient.deserialize(json.get("ingredient"));

		readAdditional(json);
	}

	// Override to read additional parameters from the json file
	protected void readAdditional(JsonObject json) {
	}

	public JsonObject write() {
		JsonObject json = new JsonObject();
		json.addProperty("cost", cost);
		json.addProperty("duration", duration);
		json.addProperty("magic", getRegistryName().toString());
		json.add("ingredient", ingredient.serialize());
		
		writeAdditional(json);
		return json;
	}

	// Override to write additional parameters to the json file
	protected void writeAdditional(JsonObject json) {
	}

	public void decode(PacketBuffer buffer) {
		cost = buffer.readFloat();
		duration = buffer.readInt();
		if (duration < 0)
			duration = HOUR;
		ingredient = Ingredient.read(buffer);
		decodeAdditional(buffer);
	}

	// Override to read additional parameters from the packet
	protected void decodeAdditional(PacketBuffer buffer) {
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeFloat(cost);
		buffer.writeInt(duration);
		ingredient.write(buffer);
		encodeAdditional(buffer);
	}

	// Override to write additional parameters to the packet
	protected void encodeAdditional(PacketBuffer buffer) {
	}

	public final ItemStack[] getMatchingStacks() {
		return ingredient.getMatchingStacks();
	}

	protected float soundPitch(PlayerEntity player) {
		return 0.8f + player.getRNG().nextFloat() * 0.4f;
	}

	protected void playSoundServer(World world, PlayerEntity player, SoundEvent sound, float volume, float pitch) {
		world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), sound, SoundCategory.PLAYERS,
				volume, pitch);
	}

	protected final void cost(PlayerEntity player, int multiplier) {
		int whole = Experience.add(player, multiplier * cost * discount(player));
		double debt = debt(player, whole);
		player.giveExperiencePoints(-whole);
		if (debt > 0)
			player.attackEntityFrom(DamageSource.MAGIC, (float) debt);
	}

	protected final void cost(PlayerEntity player) {
		cost(player, 1);
	}

	private double discount(PlayerEntity player) {
		return 1 - 0.1 * MagicArmorItem.countMagicArmorPieces(player);
	}

	private double debt(PlayerEntity player, double amount) {
		int trueLevel = player.experienceLevel;
		amount -= player.experience * player.xpBarCap();
		while (--player.experienceLevel >= 0 && amount > 0) {
			amount -= player.xpBarCap();
		}
		player.experienceLevel = trueLevel;
		return amount;
	}

	public final int getUseDuration(ItemStack staff) {
		return duration;
	}

	public final boolean isMagicItem(ItemStack stack) {
		return ingredient.test(stack);
	}

	public abstract RenderFirstPersonMagic firstPersonRenderer();

	public abstract RenderThirdPersonMagic thirdPersonRenderer();

	public abstract UseAction getUseAction(ItemStack stack);

	public void magicStart(World world, PlayerEntity player, ItemStack staff) {
	}

	public void magicTick(World world, PlayerEntity player, ItemStack staff, int count) {
	}

	public ItemStack magicFinish(World world, PlayerEntity player, ItemStack staff) {
		return staff;
	}

	public ActionResultType magicInteractBlock(ItemUseContext context) {
		return ActionResultType.PASS;
	}

	public void magicCancel(World world, PlayerEntity player, ItemStack staff, int timeLeft) {
	}

	public Description getDescription() {
		return new Description(cost, duration, getRegistryName(), getNameArgs(), getDescrArgs());
	}

	// Override to provide args to the spellbook magic name
	protected Object[] getDescrArgs() {
		return Description.NO_ARGS;
	}

	// Override to provide args to the spellbook magic description
	protected Object[] getNameArgs() {
		return Description.NO_ARGS;
	}

	public static class Description {

		private static final Object[] NO_ARGS = new Object[0];

		private float cost;
		private int duration;
		private TranslationTextComponent name;
		private TranslationTextComponent descr;

		private Description(float cost, int duration, ResourceLocation magicName, Object[] nameArgs,
				Object[] descrArgs) {
			this.cost = cost;
			this.duration = duration;
			this.name = new TranslationTextComponent(
					"gui." + magicName.getNamespace() + "." + magicName.getPath() + ".name", nameArgs);
			this.descr = new TranslationTextComponent(
					"gui." + magicName.getNamespace() + "." + magicName.getPath() + ".description", descrArgs);
		}

		public float getCost() {
			return cost;
		}

		public int getDuration() {
			return duration;
		}

		public TranslationTextComponent getName() {
			return name;
		}

		public TranslationTextComponent getDescription() {
			return descr;
		}
	}

	// Damage Types
	private static final DamageSource MAGIC = (new DamageSource(Main.MODID + ".magic")).setMagicDamage();

	public static DamageSource magicDamage() {
		return MAGIC;
	}

	public static DamageSource magicDamage(PlayerEntity player) {
		return new EntityDamageSource(Main.MODID + ".magicplayer", player).setMagicDamage();
	}

	public static DamageSource magicDamage(Entity source, PlayerEntity player) {
		return new IndirectEntityDamageSource(Main.MODID + ".magicindirect", source, player).setMagicDamage();
	}
}
