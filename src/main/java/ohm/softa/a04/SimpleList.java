package ohm.softa.a04;

import java.util.function.Function;
public interface SimpleList<K> extends Iterable<K> {
	/**
	 * Add a given object to the back of the list.
	 */
	void add(K o);

	/**
	 * @return current size of the list
	 */
	int size();



	default void addDefault (Class<K> clazz){
		try{
			this.add(clazz.newInstance());
		}catch (InstantiationException | IllegalAccessException e){
			e.printStackTrace();
		}
	}

	/**
	 * Generate a new list using the given filter instance.
	 * @return a new, filtered list
	 */
	default SimpleList<K> filter(SimpleFilter<K> filter){
		SimpleList<K> result;
		try{
			result = (SimpleList<K>) getClass().newInstance();
		}catch (InstantiationException | IllegalAccessException e){
			result = new SimpleListImpl<>();
		}

		for(K o : this) {
			if (filter.include(o)) {
				result.add(o);
			}
		}
		return result;

	}

	default <R> SimpleList<R> map(Function<K, R> transform){
		SimpleList<R> result;
		try {
			result = (SimpleList<R>) this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			result = new SimpleListImpl<>();
		}

		for(K o : this){
			result.add(transform.apply(o));
		}
		return result;
	}
}
