package piotrusha.e_shop.base;

import static com.google.common.collect.Lists.newArrayList;

import io.vavr.Tuple2;
import io.vavr.control.Either;

import java.util.List;
import java.util.function.Function;

public class ListValidator {

    public static <Error, Dto, Entity> Either<Error, List<Tuple2<Dto, Entity>>> validateAndTransform(List<Dto> dtos,
                                                                                                     Function<Dto, Either<Error, Tuple2<Dto, Entity>>> singleValidator) {
        List<Tuple2<Dto, Entity>> validatedDtos = newArrayList();
        for (Dto dto : dtos) {
            Either<Error, Tuple2<Dto, Entity>> validatedDto = singleValidator.apply(dto);
            if (validatedDto.isLeft()) {
                return Either.left(validatedDto.getLeft());
            }
            validatedDtos.add(validatedDto.get());
        }
        return Either.right(validatedDtos);
    }

    public static <Error, Result> Either<Error, List<Result>> checkError(List<Result> listToValidate,
                                                                         Function<Result, Either<Error, Result>> singleValidator) {
        for (Result toValidate : listToValidate) {
            Either<Error, Result> validationResult = singleValidator.apply(toValidate);
            if (validationResult.isLeft()) {
                return Either.left(validationResult.getLeft());
            }
        }
        return Either.right(listToValidate);
    }

}
