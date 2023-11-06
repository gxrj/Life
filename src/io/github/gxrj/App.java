package io.github.gxrj;

import io.github.gxrj.logic.Engine;

interface App {
    static void main( String... args ) {
        try{
            Engine.run();
        }
        catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }
}