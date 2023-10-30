//SPORTS COURT ROOM - BY JOSH BARTON

//MY ROOM INTERACTS WITH ANDREW DELIS' LAUNDRY ROOM. I need his sock item in his room in order to make a shot...
//in my sports court

package Rooms;

import Game.Adventure;
import Items.Item;

public class SportsCourt extends Room {

    boolean hasBall;

    boolean haveSocks;
    public SportsCourt(String name, String description) {
        super(name, description);
        Item ball = new Item("ball", "Can bring instant fame and glory");
        Item gatorade = new Item("gatorade", "It's hard to play well without it");
        //Item socks = new Item("socks", "gotta have em");
        items_.add(ball);
        items_.add(gatorade);
        //items_.add(socks);

    }

    @Override
    public void print() {
        super.print();
        System.out.println("What to do in here:");
        System.out.println("Read Plaque");
        System.out.println("To shoot the ball, type shoot ball");
    }

    @Override
    public void playerEntered() {
        System.out.println( "Take a look around." );
    }


    //handle command for the room
    @Override
    public boolean handleCommand( String[] subcommands ) {

        if( subcommands.length <= 1 ) {
            return false;
        }
        String cmd  = subcommands[0];
        String attr = subcommands[1];

        if ( cmd.equals( "Read" ) && attr.equals( "Plaque" )) {
            System.out.println("This room was created by Josh Barton and is here to improve your shooting stroke.");
            System.out.println("Make sure you have something between your sneakers and feet or your stroke will be off.");
            System.out.println("Enjoy some gatorade while you're at it!");
        }

        // unlock, use
        if( cmd.equals( "shoot" ) && attr.equals( "ball") ) {

            hasBall = false;
            haveSocks = false;
            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "ball" ) ) {
                    hasBall = true;
                    break;
                }
            }

            for( Item item: Adventure.inventory ) {
                if (item.getName().equals( "Socks") ) {
                    haveSocks = true;
                    break;
                }
            }
            if( hasBall ) {
                System.out.println( "You shoot the ball...");
            }
            else {
                System.out.println( "You don't have a ball." );
            }

            if( hasBall && haveSocks) {
                System.out.println("You scored!");
            }
            else {
                System.out.println("You missed! You may need something on your feet.");
            }
            return true;
        }
        return false;
    }


}



//maybe take the ball to the balcony to display your fame and glory


//    DONE! - Your room must do something of interest and allow the player to interact with it.
//    DONE! - It must keep track of some "state" - in other words, data that remains valid whether the player is in the room or not - and allow the player's interactions to change that state.
//    Your room must contain at least one item and it must also allow interaction with at least one item (that is not your own).  See below.
//    DONE! - Your room must respond to at least one command from the player.
//    DONE! - Your room must be linked into the house off of the Grand Hall.
//    DONE! - Make sure to add a plaque to your room that tells the reader who created the room and anything else you would like them to know.
