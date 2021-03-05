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
public abstract class Algor1 implements CStrategy {

    @Override
    public void algoritmoInterfaz1() {
        calcula();
    }

    public abstract void calcula();
}
