package io.github.gxrj.logic;

import io.github.gxrj.display.Grid;

public class Engine {
    private static Grid screen;

    public static void run() throws Exception {
        run( 15, 60, 200, 0.77f );
    }

    public static void run( Integer gridHeight, Integer gridWidth, Integer limitOfGenerations, Float birthRate ) throws Exception {
        screen = new Grid( gridHeight, gridWidth );
        var ticks = 0;
        seedContent( birthRate );

        do{
            screen.render();
            System.out.println( "Generation: " + (ticks + 1) );
            update();
            Thread.sleep( 100 );
        } while( ++ticks < limitOfGenerations );
    }

    private static void seedContent( Float birthRate ) {

        for( int line = 0; line < screen.lines(); line++ )
            for( int column = 0; column < screen.columns(); column++ )
                Grid.cells[line][column].alive = Math.random() > birthRate;
    }

    private static void update() {
        updateNeighbours();
        for( int line = 0; line < screen.lines(); line++ )
            for( int column = 0; column < screen.columns(); column++ ) {
                evolveCells( Grid.cells[line][column] );
            }
    }

    private static void evolveCells( Cell cell ) {
        cell.alive = cell.alive ? cell.neighbours > 1 && cell.neighbours < 4 : cell.neighbours == 3;
    }

    private static void updateNeighbours() {
        for( int line = 0; line < screen.lines(); line++ )
            for( int column = 0; column < screen.columns(); column++ )
                Grid.cells[line][column].neighbours = getNeighboursPerCell( line, column );
    }

    private static int getNeighboursPerCell( Integer line, Integer column ) {
        var neighbours = 0;

        var minLine = getMinValidIndex( line );
        var maxLine = getMaxValidIndex( line, screen.lines() );
        var minColumn = getMinValidIndex( column );
        var maxColumn = getMaxValidIndex( column, screen.columns() );

        for( int y = minLine; y <= maxLine; y++ ) 
            for( int x = minColumn; x <= maxColumn; x++ )
                neighbours += ( y != line || x != column ) && Grid.cells[y][x].alive ? 1 : 0;

        return neighbours;
    }

    private static int getMinValidIndex( int index ) {
        return index - 1 < 0 ? 0 : index - 1;
    }

    private static int getMaxValidIndex( int index, int limit ) {
        return index + 1 > limit - 1 ? limit - 1 : index + 1;
    }
}
