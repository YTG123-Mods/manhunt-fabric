package io.github.ytg1234.manhunt.base.config;

public final class Behaviours {
    private Behaviours() {}

    public enum Compass {
        /*
         * Compass Updates automatically every tick
         */
        UPDATE,
        /*
         * Hunter has to use compass to update it
         */
        USE
    }

    public enum Damage {
        DAMAGE, KILL
    }
}
