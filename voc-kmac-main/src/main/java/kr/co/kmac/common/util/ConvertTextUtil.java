package kr.co.kmac.common.util;

import org.apache.poi.ss.usermodel.Cell;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ConvertTextUtil {

	// Excel Cell Value
	public static Object getExcelCellValue(Cell cell) {
		if(null != cell) {
			switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				return String.valueOf((int) cell.getNumericCellValue());
			case BOOLEAN:
				return cell.getBooleanCellValue();
			}
		}
        return null;
    }

    public static <T> List<T> deduplication(final List<T> list,Function<? super T,? > key) {
        return list.stream()
                .filter(deduplication(key))
                .collect(Collectors.toList());
    }

    public static <T> Predicate<T> deduplication(Function<? super T, ?> key) {
        final Set<Object> set = ConcurrentHashMap.newKeySet();
        return predicate -> set.add(key.apply(predicate));
    }

}
