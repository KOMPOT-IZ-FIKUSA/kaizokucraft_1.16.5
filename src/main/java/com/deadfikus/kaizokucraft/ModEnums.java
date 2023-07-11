package com.deadfikus.kaizokucraft;

public class ModEnums {
    public enum HakiType {
        ATTACK, OBSERVATION
    }

    public enum Fruit {
        NONE, GOMU, BARI,
        ;
        public static Fruit[] values = Fruit.values();


        public short getI() {
            for (short i = 0; i < values.length; i++) {
                if (this == values[i]) {
                    return i;
                }
            }
            return 0;
        }
    }

    public enum AbilityGroup {
        FRUIT, HAKI, FIGHTING_STYLE, SKILL, CYBORG;
        public static AbilityGroup[] values = AbilityGroup.values();


        public short getI() {
            switch (this) {
                case FRUIT:
                    return 0;
                case HAKI:
                    return 1;
                case FIGHTING_STYLE:
                    return 2;
                case SKILL:
                    return 3;
                case CYBORG:
                    return 4;
                default:
                    ModMain.logError("Trying to get AbilityGroup identifier for " + this);
                    return 0;
            }
        }
    }

    public enum AbilityType {
        PROJECTILE, HIT, BUF, BUILDING;

        public static AbilityType[] values = AbilityType.values();


        public short getI() {
            switch (this) {
                case PROJECTILE:
                    return 0;
                case HIT:
                    return 1;
                case BUF:
                    return 2;
                case BUILDING:
                    return 3;
                default:
                    ModMain.logError("Trying to get AbilityGroup identifier for " + this);
                    return 0;
            }
        }
    }

    public enum AbilityPhase {
        CHARGING, WORKING, LOADING, READY, UNAVAILABLE;

        public static AbilityPhase[] values = AbilityPhase.values();


        public short getI() {
            switch (this) {
                case CHARGING:
                    return 0;
                case WORKING:
                    return 1;
                case LOADING:
                    return 2;
                case READY:
                    return 3;
                case UNAVAILABLE:
                    return 4;
                default:
                    ModMain.logError("Trying to get AbilityPhase identifier for " + this);
                    return 3;
            }
        }
    }


    public enum Race {
        FISHMAN, HUMAN, NONE;

        public static Race[] values = Race.values();


        public short getI() {
            switch (this) {
                case NONE:
                    return 0;
                case HUMAN:
                    return 1;
                case FISHMAN:
                    return 2;
                default:
                    ModMain.logError("Trying to get Race identifier for " + this);
                    return 0;
            }
        }
    }

    public enum Fraction {
        NONE, MARINE, PIRATE;

        public static Fraction[] values = Fraction.values();


        public short getI() {
            switch (this) {
                case NONE:
                    return 0;
                case MARINE:
                    return 1;
                case PIRATE:
                    return 2;
                default:
                    ModMain.logError("Trying to get Fraction identifier for " + this);
                    return 0;
            }
        }
    }

    public enum ExplosionType {
        STANDARD, LASER
    }

    public static class DamageSource {
        public static final net.minecraft.util.DamageSource BULLET = new net.minecraft.util.DamageSource("bullet").setProjectile();
    }
}
