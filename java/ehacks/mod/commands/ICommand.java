/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.commands;

/**
 *
 * @author radioegor146
 */
public interface ICommand {
    public String getName();
    
    public void process(String[] args);
    
    public String getCommandDescription();
    
    public String getCommandArgs();
    
    public String[] autoComplete(String[] args);
}
