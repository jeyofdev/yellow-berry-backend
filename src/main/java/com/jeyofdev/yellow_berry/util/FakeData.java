package com.jeyofdev.yellow_berry.util;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class FakeData {
    public static void generate(
            int count,
            Supplier<List<String>> dataSupplier,
            Predicate<List<String>> existsCheck,
            int maxRange,
            Consumer<List<String>> saveDTO
    ) {
        if (count == 0) {
            IntStream.range(0, maxRange).forEach((_) -> {
                List<String> data;
                do {
                    data = dataSupplier.get();
                } while (existsCheck.test(data));

                saveDTO.accept(data);
            });
        }
    }
}
