package at.ac.tuwien.infosys.aic13;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import at.ac.tuwien.infosys.aic13.dao.DTO;



public class Utils {
	@SuppressWarnings("unchecked")
	public static <X extends DTO> X lazyLoad(X dto, int level) {
		if (level == 0 || dto == null)
			return dto;
		Method[] methods = dto.getClass().getMethods();
		final Object[] emptyArgs = new Object[] {};
		for (Method m : methods) {
			if (DTO.class.isAssignableFrom(m.getReturnType())) {
				if (m.getParameterTypes().length == 0) {
					try {
						DTO childDto = (DTO) m.invoke(dto, emptyArgs);
						lazyLoad(childDto, level - 1);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (Collection.class.isAssignableFrom(m.getReturnType())) {

				try {
					@SuppressWarnings("rawtypes")
					Collection collection = (Collection) m.invoke(dto,
							emptyArgs);
					if (collection != null) {
						collection.size();
						if (!collection.isEmpty() && level > 1) {
							// If there are elements in the list and the level
							// is greater than 0 iterate the Collection and
							// lazy-Load
							Iterator<Object> it = collection.iterator();
							if (it.hasNext()) {
								Object elem = it.next();
								if (DTO.class.isAssignableFrom(elem.getClass())) {
									// LazyLoad the list.
									lazyLoad(collection, level - 1);
								}
							}

						}
					}

				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return dto;
	}

	/**
	 * Lazy load.
	 * 
	 * @param <X>
	 *            the generic type
	 * @param dtoList
	 *            the dto list
	 * @param level
	 *            the level
	 */
	public static <X extends DTO> void lazyLoad(Iterable<X> dtoList, int level) {
		if (dtoList == null || level == 0)
			return;
		for (X dto : dtoList) {
			if (dto == null)
				continue;
			lazyLoad(dto, level);
		}
	}

}