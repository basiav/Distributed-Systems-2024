package sr.ice.server;

import Demo.A;
import Demo.Calc;
import Demo.EmptySequenceException;
import com.zeroc.Ice.Current;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.OptionalLong;

public class CalcI implements Calc {
	private static final long serialVersionUID = -2448962912780867770L;
	long counter = 0;

	@Override
	public long add(int a, int b, Current __current) {
		System.out.println("ADD: a = " + a + ", b = " + b + ", result = " + (a + b));

		if (a > 1000 || b > 1000) {
			try {
				Thread.sleep(6000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}

		if (__current.ctx.values().size() > 0) {
			System.out.println("There are some properties in the context");
		}

		System.out.println("__current.id: " + __current.id);
		return a + b;
	}

	@Override
	public long subtract(int a, int b, Current __current) {
		return a-b;
	}

	@Override
	public long avg(long[] nums, Current current) throws EmptySequenceException {
		OptionalDouble optional = Arrays.stream(nums).average();
		if (optional.isPresent()) {
			return (long) optional.getAsDouble();
		}
		throw new EmptySequenceException();
	}


	@Override
	public /*synchronized*/ void op(A a1, short b1, Current current) {
		System.out.println("OP" + (++counter));
		try {
			Thread.sleep(500);
		} catch (java.lang.InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}