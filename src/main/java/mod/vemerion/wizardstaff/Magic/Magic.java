package mod.vemerion.wizardstaff.Magic;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.capability.Experience;
import mod.vemerion.wizardstaff.item.MagicArmorItem;
import mod.vemerion.wizardstaff.renderer.WizardStaffLayer.RenderThirdPersonMagic;
import mod.vemerion.wizardstaff.renderer.WizardStaffTileEntityRenderer.RenderFirstPersonMagic;
import net.minecraft.locale.Language;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public abstract class Magic {

	public static final int HOUR = 72000;

	protected float cost;
	protected int duration;
	protected Ingredient ingredient;
	private MagicType<?> type;
	private ResourceLocation name;

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

	public void setName(ResourceLocation name) {
		this.name = name;
	}
	
	public ResourceLocation getName() {
		return name;
	}

	public void read(JsonObject json) {
		cost = GsonHelper.getAsFloat(json, "cost");
		if (cost < 0)
			throw new JsonSyntaxException("The cost of a magic can not be negative");
		duration = GsonHelper.getAsInt(json, "duration");
		if (duration < 0)
			duration = HOUR;
		ingredient = Ingredient.fromJson(json.get("ingredient"));

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
		json.add("ingredient", ingredient.toJson());

		writeAdditional(json);
		return json;
	}

	// Override to write additional parameters to the json file
	protected void writeAdditional(JsonObject json) {
	}

	public void decode(FriendlyByteBuf buffer) {
		cost = buffer.readFloat();
		duration = buffer.readInt();
		if (duration < 0)
			duration = HOUR;
		ingredient = Ingredient.fromNetwork(buffer);
		decodeAdditional(buffer);
	}

	// Override to read additional parameters from the packet
	protected void decodeAdditional(FriendlyByteBuf buffer) {
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeFloat(cost);
		buffer.writeInt(duration);
		ingredient.toNetwork(buffer);
		encodeAdditional(buffer);
	}

	// Override to write additional parameters to the packet
	protected void encodeAdditional(FriendlyByteBuf buffer) {
	}

	public final ItemStack[] getMatchingStacks() {
		return ingredient.getItems();
	}

	protected float soundPitch(Player player) {
		return 0.8f + player.getRandom().nextFloat() * 0.4f;
	}

	protected void playSoundServer(Level level, Player player, SoundEvent sound, float volume, float pitch) {
		level.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS,
				volume, pitch);
	}

	protected final void cost(Player player, int multiplier) {
		int whole = Experience.add(player, multiplier * cost * discount(player));
		double debt = debt(player, whole);
		player.giveExperiencePoints(-whole);
		if (debt > 0)
			player.hurt(DamageSource.MAGIC, (float) debt);
	}

	protected final void cost(Player player) {
		cost(player, 1);
	}

	private double discount(Player player) {
		return 1 - 0.1 * MagicArmorItem.countMagicArmorPieces(player);
	}

	private double debt(Player player, double amount) {
		int trueLevel = player.experienceLevel;
		amount -= player.experienceProgress * player.getXpNeededForNextLevel();
		while (--player.experienceLevel >= 0 && amount > 0) {
			amount -= player.getXpNeededForNextLevel();
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

	public abstract UseAnim getUseAnim(ItemStack stack);

	public void magicStart(Level level, Player player, ItemStack staff) {
	}

	public void magicTick(Level level, Player player, ItemStack staff, int count) {
	}

	public ItemStack magicFinish(Level level, Player player, ItemStack staff) {
		return staff;
	}

	public InteractionResult magicInteractBlock(UseOnContext context) {
		return InteractionResult.PASS;
	}

	public void magicCancel(Level level, Player player, ItemStack staff, int timeLeft) {
	}

	public Description getDescription() {
		return new Description(this);
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
		private TranslatableComponent name;
		private TranslatableComponent descr;

		private Description(Magic magic) {
			this.cost = magic.cost;
			this.duration = magic.duration;

			ResourceLocation regName = magic.getRegistryName();
			String customName = "gui." + magic.name.getNamespace() + "." + magic.name.getPath() + ".name";
			String standardName = "gui." + regName.getNamespace() + "." + regName.getPath() + ".name";

			String customDescr = "gui." + magic.name.getNamespace() + "." + magic.name.getPath() + ".description";
			String standardDescr = "gui." + regName.getNamespace() + "." + regName.getPath() + ".description";

			this.name = new TranslatableComponent(hasTranslation(customName) ? customName : standardName,
					magic.getNameArgs());
			this.descr = new TranslatableComponent(hasTranslation(customDescr) ? customDescr : standardDescr,
					magic.getDescrArgs());
		}

		private static boolean hasTranslation(String key) {
			return Language.getInstance().has(key);
		}

		public float getCost() {
			return cost;
		}

		public int getDuration() {
			return duration;
		}

		public TranslatableComponent getName() {
			return name;
		}

		public TranslatableComponent getDescription() {
			return descr;
		}
	}

	// Damage Types
	private static final DamageSource MAGIC = (new DamageSource(Main.MODID + ".magic")).setMagic();

	public static DamageSource magicDamage() {
		return MAGIC;
	}

	public static DamageSource magicDamage(Player player) {
		return new EntityDamageSource(Main.MODID + ".magicplayer", player).setMagic();
	}

	public static DamageSource magicDamage(Entity source, Player player) {
		return new IndirectEntityDamageSource(Main.MODID + ".magicindirect", source, player).setMagic();
	}
}
