/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.internal;

public class CEColor {
    public float R = 0.0f;
    public float G = 0.0f;
    public float B = 0.0f;

    public CEColor(float r, float b, float g) {
        this.R = r;
        this.G = g;
        this.B = b;
    }

    public static CEColor colorToGL(CEColor color) {
        return new CEColor(color.R / 255.0f, color.G / 255.0f, color.B / 255.0f);
    }

    public static CEColor GLToColor(CEColor color) {
        return new CEColor(color.R * 255.0f, color.G * 255.0f, color.B * 255.0f);
    }
}

