package io.github.gxrj.display;

import java.io.IOException;

import io.github.gxrj.logic.Cell;

public record Grid( Integer lines, Integer columns ) {
    public static Cell[][] cells;

    public Grid {
        cells = new Cell[lines][columns];
        initContent( lines, columns );
    }

    private void initContent( Integer lines, Integer columns ) {

        for( int line = 0; line < lines; line++ )
            for( int column = 0; column < columns; column++ ) 
                cells[line][column] = new Cell();
    }

    public void render() throws Exception {
        clear();

        for( int line = 0; line < lines(); line++ ) {
            for( int column = 0; column < columns(); column++ )
                System.out.print( cells[line][column].alive ? "*" : " " );
            System.out.println();
        }
    }

    private void clear() throws IOException, InterruptedException {
        if( System.getProperty( "os.name" ).contains( "Windows" ) )
            new ProcessBuilder( "cmd.exe", "/c", "cls" ).inheritIO().start().waitFor();
        else
            System.out.print( "\033\143" );
    }
}
