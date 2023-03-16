package hlib.frc.table;

/**
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 * @author Andrew Hwang (u.andrew.h@gmail.com)
 */
public class RelayOperator extends Operator{

	public RelayOperator(Operator... nextOperators) {
		super(nextOperators);
	}

	@Override
	protected void process(SubscriptionRecord record) {
		if (record != null)
			output(record);
	}

}
