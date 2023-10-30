package Rooms;
import Game.Adventure;
import Items.Item;

public class LaundryRoom extends Room{

    private boolean isOutOfOrder = true;

    public LaundryRoom (String name, String description) {
        super(name, description );
        Item socks = new Item("Socks", "A pair of gym socks");
        items_.add( socks );
    }

    @Override
    public void playerEntered() {
        System.out.println( "You enter a laundry room created by Andrew Delis." );
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Type 'throw ball' if you have that item.\n");
    }

    public boolean handleCommand( String[] subcommands ) {

        if( subcommands.length <= 1 ) {
            return false;
        }
        String cmd  = subcommands[0];
        String attr = subcommands[1];

        if( cmd.equals( "read" ) && attr.equals( "plaque") ) {
            System.out.println(
                    "There is one washing machine in the room with single pair of socks on it. \n" +
                            "There is also a sign on the washing machine that reads: 'Out of Order' "
            );
        }

        // unlock, use
        if( cmd.equals( "throw" ) && attr.equals( "ball") ) {

            boolean hasBall = false;

            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "ball" ) ) {
                    hasBall = true;
                    break;
                }
            }
            if( hasBall ) {
                System.out.println( "You fixed the washing machine.");
                isOutOfOrder = false;
            }
            else {
                System.out.println( "You don't have a ball." );
            }
            return true;
        }
        return false;
    }
}
