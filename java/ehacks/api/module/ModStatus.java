/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.api.module;

import ehacks.mod.util.GLUtils;

/**
 *
 * @author radioegor146
 */
public enum ModStatus {
    DEFAULT(12303291),
    WORKING(GLUtils.getColor(0, 255, 0)),
    NOTWORKING(GLUtils.getColor(255, 100, 100)),
    CATEGORY(GLUtils.getColor(255, 255, 255));

    public int color;

    ModStatus(int color) {
        this.color = color;
    }
}
