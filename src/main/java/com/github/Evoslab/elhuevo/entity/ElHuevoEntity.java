package com.github.Evoslab.elhuevo.entity;

import com.github.Evoslab.elhuevo.Elhuevo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class ElHuevoEntity extends TameableEntity {
    private static final TrackedData<Boolean> BEGGING;
    private static final TrackedData<Integer> COLLAR_COLOR;
    public static final Predicate<LivingEntity> FOLLOW_TAMED_PREDICATE;
    private float begAnimationProgress;
    private float lastBegAnimationProgress;
    private boolean furWet;
    private boolean canShakeWaterOff;
    private float shakeProgress;
    private float lastShakeProgress;
    private UUID targetUuid;

    public ElHuevoEntity(EntityType<? extends ElHuevoEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new ElHuevoEntity.AvoidLlamaGoal(this, LlamaEntity.class, 24.0F, 1.5D, 1.5D));
        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
        this.targetSelector.add(5, new FollowTargetIfTamedGoal(this, AnimalEntity.class, false, FOLLOW_TAMED_PREDICATE));
        this.targetSelector.add(6, new FollowTargetIfTamedGoal(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
        this.targetSelector.add(7, new FollowTargetGoal(this, AbstractSkeletonEntity.class, false));
        this.targetSelector.add(8, new UniversalAngerGoal(this, true));
    }

    public static DefaultAttributeContainer.Builder createWolfAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D).add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BEGGING, false);
        this.dataTracker.startTracking(COLLAR_COLOR, DyeColor.RED.getId());
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putByte("CollarColor", (byte)this.getCollarColor().getId());
    }


    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(tag.getInt("CollarColor")));
        }
    }

    protected SoundEvent getAmbientSound() {
        if (this.random.nextInt(3) == 0) {
            return this.isTamed() && this.getHealth() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT;
        } else {
            return SoundEvents.ENTITY_WOLF_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.furWet && !this.canShakeWaterOff && !this.isNavigating() && this.onGround) {
            this.canShakeWaterOff = true;
            this.shakeProgress = 0.0F;
            this.lastShakeProgress = 0.0F;
            this.world.sendEntityStatus(this, (byte)8);
        }

    }

    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.lastBegAnimationProgress = this.begAnimationProgress;
            if (this.isBegging()) {
                this.begAnimationProgress += (1.0F - this.begAnimationProgress) * 0.4F;
            } else {
                this.begAnimationProgress += (0.0F - this.begAnimationProgress) * 0.4F;
            }

            if (this.isWet()) {
                this.furWet = true;
                if (this.canShakeWaterOff && !this.world.isClient) {
                    this.world.sendEntityStatus(this, (byte)56);
                    this.method_31167();
                }
            } else if ((this.furWet || this.canShakeWaterOff) && this.canShakeWaterOff) {
                if (this.shakeProgress == 0.0F) {
                    this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                }

                this.lastShakeProgress = this.shakeProgress;
                this.shakeProgress += 0.05F;
                if (this.lastShakeProgress >= 2.0F) {
                    this.furWet = false;
                    this.canShakeWaterOff = false;
                    this.lastShakeProgress = 0.0F;
                    this.shakeProgress = 0.0F;
                }

                if (this.shakeProgress > 0.4F) {
                    float f = (float)this.getY();
                    int i = (int)(MathHelper.sin((this.shakeProgress - 0.4F) * 3.1415927F) * 7.0F);
                    Vec3d vec3d = this.getVelocity();

                    for(int j = 0; j < i; ++j) {
                        float g = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                        float h = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                        this.world.addParticle(ParticleTypes.SPLASH, this.getX() + (double)g, (double)(f + 0.8F), this.getZ() + (double)h, vec3d.x, vec3d.y, vec3d.z);
                    }
                }
            }

        }
    }

    private void method_31167() {
        this.canShakeWaterOff = false;
        this.shakeProgress = 0.0F;
        this.lastShakeProgress = 0.0F;
    }

    public void onDeath(DamageSource source) {
        this.furWet = false;
        this.canShakeWaterOff = false;
        this.lastShakeProgress = 0.0F;
        this.shakeProgress = 0.0F;
        super.onDeath(source);
    }

    /**
     * Returns whether this wolf's fur is wet.
     * <p>
     * The wolf's fur will remain wet until the wolf shakes.
     */
    @Environment(EnvType.CLIENT)
    public boolean isFurWet() {
        return this.furWet;
    }

    /**
     * Returns this wolf's brightness multiplier based on the fur wetness.
     * <p>
     * The brightness multiplier represents how much darker the wolf gets while its fur is wet. The multiplier changes (from 0.75 to 1.0 incrementally) when a wolf shakes.
     *
     * @param tickDelta Progress for linearly interpolating between the previous and current game state.
     * @return Brightness as a float value between 0.75 and 1.0.
     * @see net.minecraft.client.render.entity.model.TintableAnimalModel#setColorMultiplier(float, float, float)
     */
    @Environment(EnvType.CLIENT)
    public float getFurWetBrightnessMultiplier(float tickDelta) {
        return Math.min(0.5F + MathHelper.lerp(tickDelta, this.lastShakeProgress, this.shakeProgress) / 2.0F * 0.5F, 1.0F);
    }

    @Environment(EnvType.CLIENT)
    public float getShakeAnimationProgress(float tickDelta, float f) {
        float g = (MathHelper.lerp(tickDelta, this.lastShakeProgress, this.shakeProgress) + f) / 1.8F;
        if (g < 0.0F) {
            g = 0.0F;
        } else if (g > 1.0F) {
            g = 1.0F;
        }

        return MathHelper.sin(g * 3.1415927F) * MathHelper.sin(g * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
    }

    @Environment(EnvType.CLIENT)
    public float getBegAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastBegAnimationProgress, this.begAnimationProgress) * 0.15F * 3.1415927F;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8F;
    }

    public int getLookPitchSpeed() {
        return this.isInSittingPose() ? 20 : super.getLookPitchSpeed();
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getAttacker();
            this.setSitting(false);
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.damage(source, amount);
        }
    }

    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
            this.dealDamage(this, target);
        }

        return bl;
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(8.0D);
        }
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || item == Items.EGG && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (this.isTamed()) {
                if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.abilities.creativeMode) {
                        itemStack.decrement(1);
                    }

                    this.heal((float)item.getFoodComponent().getHunger());
                    return ActionResult.SUCCESS;
                }

                if (!(item instanceof DyeItem)) {
                    ActionResult actionResult = super.interactMob(player, hand);
                    if ((!actionResult.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                        this.setSitting(!this.isSitting());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget((LivingEntity)null);
                        return ActionResult.SUCCESS;
                    }

                    return actionResult;
                }

                DyeColor dyeColor = ((DyeItem)item).getColor();
                if (dyeColor != this.getCollarColor()) {
                    this.setCollarColor(dyeColor);
                    if (!player.abilities.creativeMode) {
                        itemStack.decrement(1);
                    }

                    return ActionResult.SUCCESS;
                }
            } else if (item == Items.EGG) {
                if (!player.abilities.creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget((LivingEntity)null);
                    this.setSitting(true);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }

                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 8) {
            this.canShakeWaterOff = true;
            this.shakeProgress = 0.0F;
            this.lastShakeProgress = 0.0F;
        } else if (status == 56) {
            this.method_31167();
        } else {
            super.handleStatus(status);
        }

    }
    public boolean isBreedingItem(ItemStack stack) {
        return TAMING_INGREDIENT.test(stack);
    }

    public int getLimitPerChunk() {
        return 8;
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId((Integer)this.dataTracker.get(COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor color) {
        this.dataTracker.set(COLLAR_COLOR, color.getId());
    }

    public ElHuevoEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        ElHuevoEntity ElHuevoEntity = (ElHuevoEntity) Elhuevo.EL_HUEVE.create(serverWorld);
        UUID uUID = this.getOwnerUuid();
        if (uUID != null) {
            ElHuevoEntity.setOwnerUuid(uUID);
            ElHuevoEntity.setTamed(true);
        }

        return ElHuevoEntity;
    }

    public void setBegging(boolean begging) {
        this.dataTracker.set(BEGGING, begging);
    }

    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(other instanceof ElHuevoEntity)) {
            return false;
        } else {
            ElHuevoEntity ElHuevoEntity = (ElHuevoEntity)other;
            if (!ElHuevoEntity.isTamed()) {
                return false;
            } else if (ElHuevoEntity.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && ElHuevoEntity.isInLove();
            }
        }
    }

    public boolean isBegging() {
        return (Boolean)this.dataTracker.get(BEGGING);
    }

    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof ElHuevoEntity) {
                ElHuevoEntity ElHuevoEntity = (ElHuevoEntity)target;
                return !ElHuevoEntity.isTamed() || ElHuevoEntity.getOwner() != owner;
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).shouldDamagePlayer((PlayerEntity)target)) {
                return false;
            } else if (target instanceof HorseBaseEntity && ((HorseBaseEntity)target).isTame()) {
                return false;
            } else {
                return !(target instanceof TameableEntity) || !((TameableEntity)target).isTamed();
            }
        } else {
            return false;
        }
    }

    public boolean canBeLeashedBy(PlayerEntity player) {
        return !super.canBeLeashedBy(player);
    }

    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0D, (double)(0.6F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    private static final Ingredient TAMING_INGREDIENT;

    static {
        TAMING_INGREDIENT = Ingredient.ofItems(Items.COD, Items.SALMON);
        BEGGING = DataTracker.registerData(ElHuevoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        COLLAR_COLOR = DataTracker.registerData(ElHuevoEntity.class, TrackedDataHandlerRegistry.INTEGER);
        FOLLOW_TAMED_PREDICATE = (livingEntity) -> {
            EntityType<?> entityType = livingEntity.getType();
            return entityType == EntityType.SHEEP || entityType == EntityType.RABBIT || entityType == EntityType.FOX;
        };
    }

    class AvoidLlamaGoal<T extends LivingEntity> extends FleeEntityGoal<T> {
        private final ElHuevoEntity wolf;

        public AvoidLlamaGoal(ElHuevoEntity wolf, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
            super(wolf, fleeFromType, distance, slowSpeed, fastSpeed);
            this.wolf = wolf;
        }

        public boolean canStart() {
            if (super.canStart() && this.targetEntity instanceof LlamaEntity) {
                return !this.wolf.isTamed() && this.isScaredOf((LlamaEntity)this.targetEntity);
            } else {
                return false;
            }
        }

        private boolean isScaredOf(LlamaEntity llama) {
            return llama.getStrength() >= ElHuevoEntity.this.random.nextInt(5);
        }

        public void start() {
            ElHuevoEntity.this.setTarget((LivingEntity)null);
            super.start();
        }

        public void tick() {
            ElHuevoEntity.this.setTarget((LivingEntity)null);
            super.tick();
        }
    }
}

