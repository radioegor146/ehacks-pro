/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.external.config;

/**
 *
 * @author radioegor146
 */
public interface IConfiguration {

    void read();

    void write();

    String getConfigFilePath();

    boolean isConfigUnique();
}
