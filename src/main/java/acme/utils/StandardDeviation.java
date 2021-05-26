package acme.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StandardDeviation {

	private StandardDeviation() {
	    throw new IllegalStateException("Utility class");
	  }

    public static double calculateSD(List<Double> values)
    {
    	
    	values = values.stream().filter(Objects::nonNull).collect(Collectors.toList());
    	
        double sum = 0.0, standardDeviation = 0.0;
        final int length = values.size();

        for(final double num : values) {
            sum += num;
        }

        final double mean = sum/length;

        for(final double num: values) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }
}