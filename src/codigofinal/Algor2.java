/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigofinal;

/**
 *
 * @author Padilla
 */
public abstract class Algor2 implements CStrategy {

    @Override
    public void algoritmoInterfaz1() {
        ordena();
    }

    public abstract void ordena();

}
