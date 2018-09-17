package vincent.assignment1.model;


/**
 * @author Yu Liu
 *
 * some attributes cannot read from tracking_data.txt, such as station name
 * and there is not compulsive requirement
 * so I choose use set method to get attribute, instead of a fully implementing constructor
 *
 * Maybe I would apply build pattern to create an instance.
 */

public class SimpleTracking extends AbstractTracking {

    public SimpleTracking (){
        super();
    }
}