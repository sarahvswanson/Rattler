/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rattler;

import environment.ApplicationStarter;

/**
 *
 * @author Owner
 */
public class Rattler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        ApplicationStarter.run("Ratttttler", new SnakeEnvironment());
    }
}
