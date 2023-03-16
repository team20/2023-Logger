package hlib.frc.table;

import java.util.LinkedList;

/**
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 * @author Andrew Hwang (u.andrew.h@gmail.com)
 */
public abstract class Operator {

	LinkedList<Operator> nextOperators = new LinkedList<Operator>();

	public Operator(Operator... nextOperators) {
		for (Operator nextOperator : nextOperators)
			this.nextOperators.add(nextOperator);
	}

	public void addNextOperator(Operator nextOperator) {
		this.nextOperators.add(nextOperator);
	}

	public void output(SubscriptionRecord record) {
		for (Operator nextOperator : nextOperators)
			nextOperator.process(record);
	}

	protected abstract void process(SubscriptionRecord record);

}
